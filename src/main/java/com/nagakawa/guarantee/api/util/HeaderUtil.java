package com.nagakawa.guarantee.api.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public final class HeaderUtil {

	private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

	private HeaderUtil() {
	}

	/**
	 * <p>
	 * createAlert.
	 * </p>
	 *
	 * @param applicationName a {@link String} object.
	 * @param message         a {@link String} object.
	 * @param param           a {@link String} object.
	 * @return a {@link HttpHeaders} object.
	 */
	public static HttpHeaders createAlert(String message, String param) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Action-Alert", message);
		try {
			headers.add("X-Action-Params", URLEncoder.encode(param, StandardCharsets.UTF_8.toString()));
		} catch (UnsupportedEncodingException e) {
			// StandardCharsets are supported by every Java implementation so this exception
			// will never happen
		}

		return headers;
	}

	/**
	 * <p>
	 * createEntityCreationAlert.
	 * </p>
	 *
	 * @param applicationName   a {@link String} object.
	 * @param enableTranslation a boolean.
	 * @param entityName        a {@link String} object.
	 * @param param             a {@link String} object.
	 * @return a {@link HttpHeaders} object.
	 */
	public static HttpHeaders createEntityCreationAlert(String applicationName, boolean enableTranslation,
			String entityName, String param) {
		String message = enableTranslation ? applicationName + "." + entityName + ".created"
				: "A new " + entityName + " is created with identifier " + param;
		return createAlert(message, param);
	}

	/**
	 * <p>
	 * createEntityUpdateAlert.
	 * </p>
	 *
	 * @param applicationName   a {@link String} object.
	 * @param enableTranslation a boolean.
	 * @param entityName        a {@link String} object.
	 * @param param             a {@link String} object.
	 * @return a {@link HttpHeaders} object.
	 */
	public static HttpHeaders createEntityUpdateAlert(String applicationName, boolean enableTranslation,
			String entityName, String param) {
		String message = enableTranslation ? applicationName + "." + entityName + ".updated"
				: "A " + entityName + " is updated with identifier " + param;
		return createAlert(message, param);
	}

	/**
	 * <p>
	 * createEntityDeletionAlert.
	 * </p>
	 *
	 * @param applicationName   a {@link String} object.
	 * @param enableTranslation a boolean.
	 * @param entityName        a {@link String} object.
	 * @param param             a {@link String} object.
	 * @return a {@link HttpHeaders} object.
	 */
	public static HttpHeaders createEntityDeletionAlert(String applicationName, boolean enableTranslation,
			String entityName, String param) {
		String message = enableTranslation ? applicationName + "." + entityName + ".deleted"
				: "A " + entityName + " is deleted with identifier " + param;
		return createAlert(message, param);
	}

	/**
	 * <p>
	 * createFailureAlert.
	 * </p>
	 *
	 * @param applicationName   a {@link String} object.
	 * @param enableTranslation a boolean.
	 * @param entityName        a {@link String} object.
	 * @param errorKey          a {@link String} object.
	 * @param defaultMessage    a {@link String} object.
	 * @return a {@link HttpHeaders} object.
	 */
	public static HttpHeaders createFailureAlert(boolean enableTranslation, String entityName,
			String errorKey, String defaultMessage) {
		log.error("Entity processing failed, {}", defaultMessage);

		String message = enableTranslation ? "error." + errorKey : defaultMessage;

		HttpHeaders headers = new HttpHeaders();
		
		headers.add("X-Action-Alert", message);
		headers.add("X-Action-Params", entityName);
		
		return headers;
	}
}
