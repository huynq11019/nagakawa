package com.nagakawa.guarantee.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nagakawa.guarantee.api.exception.BadRequestAlertException;
import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.security.RsaProvider;
import com.nagakawa.guarantee.security.UserPrincipal;
import com.nagakawa.guarantee.security.configuration.AuthenticationProperties;
import com.nagakawa.guarantee.security.exception.InvalidTokenRequestException;
import com.nagakawa.guarantee.security.util.SecurityConstants;
import com.nagakawa.guarantee.security.util.SecurityUtils;
import com.nagakawa.guarantee.util.DateUtils;
import com.nagakawa.guarantee.util.GetterUtil;
import com.nagakawa.guarantee.util.HMACUtil;
import com.nagakawa.guarantee.util.StringPool;
import com.nagakawa.guarantee.util.StringUtil;
import com.nagakawa.guarantee.util.Validator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTTokenProvider implements InitializingBean {
    private Key key;

    private final AuthenticationProperties properties;

    private final UserDetailsService userDetailsService;

    private final RsaProvider rsaProvider;

    private LoadingCache<String, String> attemptsCache;
    
    private JwtParser jwtParser;

    @Override
	public void afterPropertiesSet() throws Exception {
		byte[] keyBytes;

		String secret = properties.getBase64Secret();

		if (StringUtils.isEmpty(secret)) {
			_log.warn("Warning: the JWT key used is not Base64-encoded. "
					+ "We recommend using the `spring.security.authentication.jwt.base64-secret` key for optimum security.");

			keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		} else {
			_log.info("Using a Base64-encoded JWT secret key");

			keyBytes = Decoders.BASE64.decode(secret);
		}

		this.key = Keys.hmacShaKeyFor(keyBytes);

		//jwt parser
		this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
		// init cache
		this.attemptsCache = CacheBuilder.newBuilder()
				.expireAfterWrite(properties.getTokenRememberMeDuration(), TimeUnit.DAYS)
				.build(new CacheLoader<String, String>() {
					@Override
					public String load(final String key) {
						return StringPool.BLANK;
					}
				});
	}

    // access token

    public Cookie clearCookie(String key) {
    	Cookie cookie = new Cookie(key, StringPool.BLANK);

    	cookie.setPath("/");
    	cookie.setMaxAge(0);
    	cookie.setHttpOnly(true);

    	return cookie;
    }

	public HttpCookie clearHttpCookie(String key) {

        return ResponseCookie.from(key, StringPool.BLANK)
        		.maxAge(0)
                .httpOnly(properties.isHttpOnly())
                .path(properties.getPath())
                .secure(properties.isEnableSsl())
                .sameSite(properties.getSameSite())
                .build();
    }

	public JWTToken createAccessToken(Authentication authentication, boolean rememberMe) {
		try {
			String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(","));

			String username = authentication.getName();

			Object ob = authentication.getPrincipal();

			UserDetails principal = ob instanceof UserPrincipal ? (UserPrincipal) ob
					: userDetailsService.loadUserByUsername(username);

			int duration = rememberMe ? properties.getTokenDuration() : properties.getTokenRememberMeDuration();

			return createAccessToken(((UserPrincipal) principal).getUser(), username, authorities, duration);
		} catch (UsernameNotFoundException e) {
		    _log.error(Labels.getLabels(LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD));

		    throw new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD),
                    User.class.getSimpleName(), LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD);
		}
	}

    /*
     * Create refresh token
     */

	public JWTToken createAccessToken(String username) {
		try {
			UserDetails principal = userDetailsService.loadUserByUsername(username);

			String authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(","));

			return createAccessToken(((UserPrincipal) principal).getUser(), username, authorities,
					properties.getTokenDuration());
        } catch (UsernameNotFoundException e) {
            _log.error(Labels.getLabels(LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD));

            throw new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD),
                    User.class.getSimpleName(), LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD);
        }
	}

	public JWTToken createAccessToken(User user, String username, String authorities, int duration) {
    	Date expiration = DateUtils.getDateAfter(new Date(), duration);

    	String jwt = Jwts.builder()
                .setSubject(username)
                .claim(SecurityConstants.Header.PRIVILEGES, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(new Date())
                .setExpiration(expiration).compact();

    	this.attemptsCache.put(username, jwt);

        return new JWTToken(jwt, duration * 24 * 60 * 60);
    }

    public Cookie createCookie(String key, String value) {
    	Cookie cookie = new Cookie(key, value);

    	cookie.setPath("/");
    	cookie.setHttpOnly(true);

    	return cookie;
    }

    public HttpCookie createHttpCookie(String key, String value, int duration) {

        return ResponseCookie.from(key, value)
        		.maxAge(duration)
                .httpOnly(properties.isHttpOnly())
                .path(properties.getPath())
                .secure(properties.isEnableSsl())
                .sameSite(properties.getSameSite())
                .build();
    }

    public JWTToken createRefreshToken(Authentication authentication) {
		String username = authentication.getName();

		String refreshToken = HMACUtil.hashSha256(UUID.randomUUID().toString() + username);

		return new JWTToken(refreshToken, properties.getRefeshTokenDuration() * 24 * 60 * 60);
	}

    public Authentication getAuthentication(String token) {
		Claims claims = jwtParser.parseClaimsJws(token).getBody();

		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(SecurityConstants.Header.PRIVILEGES).toString().split(","))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		String username = claims.getSubject();

		UserPrincipal principal = (UserPrincipal) userDetailsService.loadUserByUsername(username);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

    public void invalidateToken() {
        Optional<User> userOptional = SecurityUtils.getUserLogin();

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            String username = user.getUsername();

            String token = this.attemptsCache.getIfPresent(username);
            // invalidate all token belong to username in cache
            this.attemptsCache.invalidate(username);

            if (_log.isDebugEnabled()) {
                _log.debug("{} token(s) expired", token);
            }
        }
    }

    public boolean validateToken(String authToken) {
        String username = StringPool.BLANK;

        try {
            Claims claims = jwtParser.parseClaimsJws(authToken).getBody();

            username = claims.getSubject();
        } catch (MalformedJwtException ex) {
            _log.error("Invalid JWT token");

            throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");

        } catch (ExpiredJwtException ex) {
            _log.error("Expired JWT token");

            throw new InvalidTokenRequestException("JWT", authToken, "Token expired. Refresh required");

        } catch (UnsupportedJwtException ex) {
            _log.error("Unsupported JWT token");
            throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");

        } catch (IllegalArgumentException ex) {
            _log.error("JWT claims string is empty.");

            throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
        } catch (Exception e) {
            _log.error("Invalid JWT signature.", e);
        }

        return isTokenInStore(username, authToken);
    }

    public boolean validateToken(String authToken, String csrfToken) {
        String username = StringPool.BLANK;

		try {
			Claims claims = jwtParser.parseClaimsJws(authToken).getBody();

			username = claims.getSubject();

			String csrfTokenDecode = rsaProvider.decrypt(csrfToken);

			if (Validator.isNull(csrfTokenDecode)) {
				return false;
			}

			String[] csrfPart = StringUtil.split(csrfTokenDecode, StringPool.DASH);

			if (csrfPart.length != 2) {
				return false;
			}

				long timestampt = GetterUtil.getLongValue(csrfPart[0], 0);

			String principal = csrfPart[1];

			if (timestampt <= 0 || !principal.equals(username) || !invalidCsrfTimestamp(timestampt)) {
				return false;
			}
		} catch (MalformedJwtException ex) {
            _log.error("Invalid JWT token");

            throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");

        } catch (ExpiredJwtException ex) {
            _log.error("Expired JWT token");

            throw new InvalidTokenRequestException("JWT", authToken, "Token expired. Refresh required");

        } catch (UnsupportedJwtException ex) {
            _log.error("Unsupported JWT token");
            throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");

        } catch (IllegalArgumentException ex) {
            _log.error("JWT claims string is empty.");

            throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
        } catch (Exception e) {
            _log.error("Invalid JWT signature.", e);

			throw new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_BAD_REQUEST), "token",
					LabelKey.ERROR_BAD_REQUEST);
        }

        return isTokenInStore(username, authToken);
    }

    private boolean isTokenInStore(String username, String token) {
    	return Validator.isNotNull(username) && Validator.isNotNull(this.attemptsCache.getIfPresent(username));
    }

	private boolean invalidCsrfTimestamp(long timestamp) {
		Instant csrfTimestamp = Instant.ofEpochMilli(timestamp);
		Instant now = Instant.now();

		csrfTimestamp = csrfTimestamp.plusSeconds(properties.getCsrfTokenDuration());

		return csrfTimestamp.isAfter(now);
	}
}
