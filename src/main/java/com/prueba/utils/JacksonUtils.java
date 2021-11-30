package com.prueba.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtils {

	private static Logger log = LoggerFactory.getLogger(JacksonUtils.class);
	private static final String JACKSON_EXC = "JackUtilsException";

	private JacksonUtils() {

	}

	public static String getPlainJson(Object object) {
		String json = null;
		try {
			json = new ObjectMapper().writeValueAsString(object);

		} catch (JsonProcessingException e) {
			log.error(JACKSON_EXC, e);
		}
		return json;

	}

}
