package com.nagakawa.guarantee.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HMACUtil {

	/** The Constant HMACSHA256. */
	public static final String HMACSHA256 = "HmacSHA256";

	/** The Constant SHA256. */
	public static final String SHA256 = "SHA-256";

	/**
	 * Encode base 64.
	 *
	 * @param messages  the messages
	 * @param sparator  the sparator
	 * @param keyString the key string
	 * @param algo      the algo
	 * @return the string
	 */
	public static String encodeBase64(Object[] messages, String sparator, String keyString, String algo) {
		String joinMessage = StringUtil.join(messages, sparator);

		return encodeBase64(joinMessage, keyString, algo);
	}

	/**
	 * Encode base 64.
	 *
	 * @param message   the message
	 * @param keyString the key string
	 * @param algo      the algo
	 * @return the string
	 */
	public static String encodeBase64(String message, String keyString, String algo) {
		String digest = null;

		try {
			Mac hasher = Mac.getInstance(algo);

			hasher.init(new SecretKeySpec(keyString.getBytes(), algo));

			byte[] hash = hasher.doFinal(message.getBytes());

			return Base64.getEncoder().encodeToString(hash);
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			_log.error(e.getMessage(), e);
		}

		return digest;
	}

	/**
	 * Encode hex.
	 *
	 * @param messages  the messages
	 * @param sparator  the sparator
	 * @param keyString the key string
	 * @param algo      the algo
	 * @return the string
	 */
	public static String encodeHex(Object[] messages, String sparator, String keyString, String algo) {
		String joinMessage = StringUtil.join(messages, sparator);

		return encodeHex(joinMessage, keyString, algo);
	}

	/**
	 * Encode hex.
	 *
	 * @param message   the message
	 * @param keyString the key string
	 * @param algo      the algo
	 * @return the string
	 */
	public static String encodeHex(String message, String keyString, String algo) {
		String digest = null;

		try {
			Mac hasher = Mac.getInstance(algo);

			hasher.init(new SecretKeySpec(keyString.getBytes(), algo));

			byte[] hash = hasher.doFinal(message.getBytes());

			return Hex.encodeHexString(hash);
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			_log.error(e.getMessage(), e);
		}

		return digest;
	}

	/**
	 * Hash.
	 *
	 * @param messages the messages
	 * @param sparator the sparator
	 * @param algo     the algo
	 * @return the string
	 */
	public static String hash(Object[] messages, String sparator, String algo) {
		String joinMessage = StringUtil.join(messages, sparator);

		return hash(joinMessage, algo);
	}

	/**
	 * Hash.
	 *
	 * @param joinMessage the join message
	 * @param algo        the algo
	 * @return the string
	 */
	public static String hash(String joinMessage, String algo) {
		StringBuffer sb = new StringBuffer();

		try {
			MessageDigest mDigest = MessageDigest.getInstance(algo);

			byte[] result = mDigest.digest(joinMessage.getBytes());

			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}

		} catch (NoSuchAlgorithmException e) {
			_log.error(e.getMessage(), e);
		}

		return sb.toString();
	}

	/**
	 * Hash.
	 *
	 * @param message   the message
	 * @param keyString the key string
	 * @param algo      the algo
	 * @return the string
	 */
	public static String hash(String message, String keyString, String algo) {
		String digest = null;

		try {
			Mac hasher = Mac.getInstance(algo);

			hasher.init(new SecretKeySpec(keyString.getBytes(), algo));

			byte[] hash = hasher.doFinal(message.getBytes());

			return new String(hash);
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			_log.error(e.getMessage(), e);
		}

		return digest;
	}

	public static String hashSha256(String message) {
		return hash(message, SHA256);
	}

	/**
	 * Sha 256 hex.
	 *
	 * @param messages the messages
	 * @param sparator the sparator
	 * @return the string
	 */
	public static String sha256Hex(Object[] messages, String sparator) {
		String joinMessage = StringUtil.join(messages, sparator);

		return DigestUtils.sha256Hex(joinMessage);
	}


	public static String generateHS256Token(String appKey, String appSecret, long tokenExpire) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		String base64Key = DatatypeConverter.printBase64Binary(appSecret.getBytes());
		
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Key);
		
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		
		long JWT_EXPIRATION = tokenExpire;
		
		Date now = new Date();
		
		return Jwts.builder()
				.claim("iss", appKey)
				.claim("exp", now.getTime() + JWT_EXPIRATION)
				.signWith(signingKey, signatureAlgorithm)
				.compact();
	}
}
