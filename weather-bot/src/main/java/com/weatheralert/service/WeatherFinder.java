package com.weatheralert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weatheralert.model.EmbededLocation;
import com.weatheralert.model.WeatherModel;
import com.weatheralert.parsers.WeatherJsonParser;
import com.weatheralert.templates.RequestTemplate;

@Service
public class WeatherFinder {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RequestTemplate requestTemplate;
	
	@Autowired
	private WeatherJsonParser weatherJsonParser;

	public WeatherModel currentWeather(EmbededLocation location) {
		String request = requestTemplate.weatherCurrentRequest(location);
		String jsonWeather = restTemplate.getForObject(request, String.class);
		return weatherJsonParser.currentWeather(jsonWeather);
	}

	public WeatherModel onDayWeather(EmbededLocation location) {
		String request = requestTemplate.weatherOnDayRequest(location);
		String jsonWeather = restTemplate.getForObject(request, String.class);
		return weatherJsonParser.onDayWeather(jsonWeather);
	}

	public List<WeatherModel> onWeekWeather(EmbededLocation location) {
		String request = requestTemplate.weatherOnWeekRequest(location);
		String jsonStr = restTemplate.getForObject(request, String.class);
		return weatherJsonParser.onWeekWeather(jsonStr);
	}

}
