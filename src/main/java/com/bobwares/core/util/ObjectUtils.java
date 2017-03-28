package com.bobwares.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;


public class ObjectUtils {
    static final Logger logger = LoggerFactory.getLogger(ObjectUtils.class);


	public static <T> T jsonToObject(InputStream inputStream, Class<T> resultType) {
		return inputStreamToObject(inputStream, resultType, getJsonObjectMapper());
	}


	private static <T> T inputStreamToObject(InputStream inputStream, Class<T> resultType, ObjectMapper mapper) {
		if (inputStream != null) {
			try {
				return mapper.readValue(inputStream, resultType);
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}
	private static ObjectMapper getJsonObjectMapper() {
		return  new ObjectMapper();
	}
}

