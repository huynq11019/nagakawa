/**
 * 
 */
package com.nagakawa.guarantee.configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.nagakawa.guarantee.util.Validator;
import lombok.Data;

/**
 * @author LinhLH
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "validation")
public class ValidationProperties {
	private static Pattern _usernamePattern;

	private static Pattern _emailPattern;

	private static Pattern _phoneNumberPattern;

	private static Pattern _passwordPattern;
	
	private static Pattern _coordinatePattern;

	private String usernameRegex;

	private String passwordRegex;

	private String emailRegex;

	private String phoneNumberRegex;
	
	private String coordinateRegex;

	private int phoneNumberMinLength;
	
	private int phoneNumberMaxLength;

	private int fullnameMinLength;

	private int fullnameMaxLength;
	
	private int usernameMinLength;

	private int usernameMaxLength;
	
	private int passwordMinLength;

	private int passwordMaxLength;
	
	private int emailMinLength;

	private int emailMaxLength;
	
	private int codeMinLength;

	private int codeMaxLength;

	private int addressMaxLength;
	
	private int nameMaxLength;
	
	private int descriptionMaxLength;
	
	private int intNumbermaxLength;

	@PostConstruct
	public void init() {
		_usernamePattern = Pattern.compile(usernameRegex);

		_emailPattern = Pattern.compile(emailRegex);

		_phoneNumberPattern = Pattern.compile(phoneNumberRegex);

		_passwordPattern = Pattern.compile(passwordRegex);
		
		_coordinatePattern = Pattern.compile(coordinateRegex);
	}

	public boolean isCoordinateValid(String coordinate) {
		if (Validator.isNull(coordinate)) {
			return false;
		}

		Matcher matcher = _coordinatePattern.matcher(coordinate);

		return matcher.matches();
	}
	
	public boolean isEmailValid(String email) {
		if (Validator.isNull(email)) {
			return false;
		}

		Matcher matcher = _emailPattern.matcher(email);

		return matcher.matches();
	}

	public boolean isPasswordValid(String password) {
		if (Validator.isNull(password)) {
			return false;
		}

		Matcher matcher = _passwordPattern.matcher(password);

		return matcher.matches();
	}

	public boolean isPhoneNumberValid(String phoneNumber) {
		if (Validator.isNull(phoneNumber)) {
			return false;
		}

		Matcher matcher = _phoneNumberPattern.matcher(phoneNumber);

		return matcher.matches();
	}

	public boolean isUsernameValid(String username) {
		if (Validator.isNull(username)) {
			return false;
		}

		Matcher matcher = _usernamePattern.matcher(username);

		return matcher.matches();
	}
}
