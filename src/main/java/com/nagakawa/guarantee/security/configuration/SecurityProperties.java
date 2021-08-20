/**
 * 
 */
package com.nagakawa.guarantee.security.configuration;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.nagakawa.guarantee.security.RsaProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author LinhLH
 *
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "rsa")
public class SecurityProperties {
	private int keyLength;

	private String privateKey;

	private Signal signal;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Signal {
		private String publicKey;

		private String privateKey;
	}

	@Bean(name = "rsaProvider")
	public RsaProvider rsaProvider()
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		return RsaProvider.fromPrivateKey(privateKey);
	}

	@Bean(name = "signalRsaProvider")
	public RsaProvider signalRsaProvider()
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		return RsaProvider.fromPrivateKey(signal.getPrivateKey());
	}
}
