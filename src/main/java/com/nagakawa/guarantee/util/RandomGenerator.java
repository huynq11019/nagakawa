package com.nagakawa.guarantee.util;

import java.security.SecureRandom;

public class RandomGenerator {

	private static SecureRandom random = new SecureRandom();

	public static String randomWithNDigits(int n) {
		n = Math.abs(n);
		
		return String.format("%s",
				(int) Math.pow(10.0D, (double) (n - 1)) + random.nextInt(9 * (int) Math.pow(10.0D, (double) (n - 1))));
	}
}
