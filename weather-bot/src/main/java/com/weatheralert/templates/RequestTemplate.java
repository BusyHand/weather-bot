package com.weatheralert.templates;

import java.net.URI;

import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import com.weatheralert.model.EmbededLocation;

@Component
@PropertySource(value = { "classpath:secrets.properties" }, encoding = CharEncoding.UTF_8)
public class RequestTemplate {

	@Value("${weatherbit.api.key}")
	private String weatherKey;

	@Value("${geodb.api.key}")
	private String geodbKey;

	public String dataTimeZoneRequest(EmbededLocation location) {
		return weatherCurrentRequest(location);
	}

	public String weatherCurrentRequest(EmbededLocation location) {
		return "https://api.weatherbit.io/v2.0/current?" +
				"lat=" + location.getLatitude() +
				"&lon=" + location.getLongitude() +
				"&key=" + weatherKey;
	}

	public String weatherOnDayRequest(EmbededLocation location) {
		return weatherOnWeekRequest(location);
	}

	public String weatherOnWeekRequest(EmbededLocation location) {
		return "https://api.weatherbit.io/v2.0/forecast/daily?" +
				"lat=" + location.getLatitude() +
				"&lon=" + location.getLongitude() +
				"&key=" + weatherKey;
	}

	public RequestEntity<Void> dataFindTownsByName(String cityName) {
		cityName = cityName.replace(" ", "%20");
		String request = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities?" +
				"namePrefix=" + cityName +
				"&languageCode=ru";
		return RequestEntity.get(URI.create(request))
				.header("X-RapidAPI-Key", geodbKey)
				.build();
	}

}
