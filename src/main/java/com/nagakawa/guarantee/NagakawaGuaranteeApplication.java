package com.nagakawa.guarantee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class NagakawaGuaranteeApplication {

	private static final Logger _log = LoggerFactory.getLogger(NagakawaGuaranteeApplication.class);

    private final Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(NagakawaGuaranteeApplication.class, args);
	}

}
