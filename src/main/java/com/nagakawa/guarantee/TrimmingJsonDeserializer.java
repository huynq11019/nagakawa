/**
 * 
 */
package com.nagakawa.guarantee;

import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nagakawa.guarantee.util.GetterUtil;

/**
 * @author LinhLH Trim tất cả tham số String đầu vào
 */
@JsonComponent
public class TrimmingJsonDeserializer extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return p.hasToken(JsonToken.VALUE_STRING) ? GetterUtil.getString(p.getText(), null) : p.getValueAsString();
	}
}
