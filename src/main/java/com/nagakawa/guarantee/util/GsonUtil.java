/**
 * 
 */
package com.nagakawa.guarantee.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LinhLH
 *
 */
@Slf4j
public class GsonUtil {
	public static String getUserNameFromPhoneNumber(String phoneNumber) {
		//String phoneNumberHash = HMACUtil.hashSha256(phoneNumber);

		//return phoneNumberHash.substring(0, 15);
		return phoneNumber;
	}
	
	public static boolean canJsonParse(HttpEntity<String> response) {
	    if (Validator.isNotNull(response)
                && Validator.isNotNull(response.getBody())) {
	        _log.info("api call response {}", response.getBody());
	    }
	    
        return Validator.isNotNull(response)
                && Validator.isNotNull(response.getBody())
                && response.getBody().startsWith(StringPool.OPEN_CURLY_BRACE);
	}
	
	public static boolean canJsonParse(String text) {
		return Validator.isNotNull(text)
                && text.startsWith(StringPool.OPEN_CURLY_BRACE);
	}
	
	public static String extractErrorMessage(String original) {
		return StringUtils.substringBetween(original, "[", "]");
	}
	
	public static JsonObject parse(String text) {
		if (!canJsonParse(text)) {
			return null;
		}

		return JsonParser.parseString(text).getAsJsonObject();
	}
	
	public static int getInt(JsonObject ob, String elementName, int defaultValue) {
		JsonElement e = ob.get(elementName);

		return e != null && !e.isJsonNull() ? GetterUtil.getInteger(e.getAsString(), defaultValue)
				: defaultValue;
	}
}
