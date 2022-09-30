package com.weatheralert.parsers;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ParserJson {

	@Autowired
	private ObjectMapper mapper;

	protected JsonNode readTree(String jsonWeather) {
		try {
			return mapper.readTree(jsonWeather);
		} catch (JsonProcessingException e) {
			RuntimeException re = new RuntimeException();
			re.initCause(e);
			throw re;
		}
	}

}
