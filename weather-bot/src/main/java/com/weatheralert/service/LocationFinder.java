package com.weatheralert.service;

import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weatheralert.model.EmbededLocation;
import com.weatheralert.model.TownView;
import com.weatheralert.parsers.TownJsonParser;
import com.weatheralert.templates.RequestTemplate;

@Service
public class LocationFinder {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RequestTemplate requestTemplate;

	@Autowired
	private TownJsonParser townJsonParser;

	public List<TownView> findTownsByName(String cityName) {
		RequestEntity<Void> requestEntity = requestTemplate.dataFindTownsByName(cityName);
		ResponseEntity<String> townsEntities = restTemplate.exchange(requestEntity, String.class);
		return townJsonParser.findTownsByName(townsEntities.getBody());
	}

	public ZoneId findZoneTimeByLocation(EmbededLocation location) {
		String requestWeather = requestTemplate.dataTimeZoneRequest(location);
		String jsonTown = restTemplate.getForObject(requestWeather, String.class);
		return townJsonParser.findZoneTimeByLocation(jsonTown);
	}

}
