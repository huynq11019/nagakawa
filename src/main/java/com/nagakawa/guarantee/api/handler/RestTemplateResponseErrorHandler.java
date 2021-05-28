package com.nagakawa.guarantee.api.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagakawa.guarantee.api.response.ErrorResponse;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	private static final Logger log = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);

	private final ObjectMapper objectMapper;

	@Autowired
	public RestTemplateResponseErrorHandler() {
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
		return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
				|| httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {
		if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
			ErrorResponse errorResponse = objectMapper.readValue(httpResponse.getStatusText(), ErrorResponse.class);

			log.error("REST problem: {} - {}", errorResponse.getCode(), errorResponse.getMessage());
		}
	}
}
