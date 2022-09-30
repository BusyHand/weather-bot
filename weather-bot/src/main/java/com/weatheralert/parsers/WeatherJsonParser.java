package com.weatheralert.parsers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.weatheralert.model.WeatherModel;

@Component
public class WeatherJsonParser extends ParserJson {

	public WeatherModel currentWeather(String jsonWeather) {
		JsonNode treeJson = readTree(jsonWeather);
		JsonNode temperature = treeJson.findValue("temp");
		JsonNode icon = treeJson.findValue("icon");
		JsonNode desCode = treeJson.findValue("code");
		JsonNode windSpd = treeJson.findValue("wind_spd");

		WeatherModel weatherModel = new WeatherModel();
		weatherModel.setIcon(icon.asText());
		weatherModel.setTemperature(temperature.asText());
		weatherModel.setDesCode(desCode.asText());
		weatherModel.setWindSpd(windSpd.asText());
		return weatherModel;
	}

	public WeatherModel onDayWeather(String jsonWeather) {
		JsonNode treeJson = readTree(jsonWeather);
		JsonNode data = treeJson.findValue("data");
		JsonNode firstDay = data.get(0);
		JsonNode temperature = firstDay.findValue("temp");
		JsonNode icon = firstDay.findValue("icon");
		JsonNode desCode = firstDay.findValue("code");
		JsonNode windSpd = firstDay.findValue("wind_spd");
		JsonNode pop = firstDay.findValue("pop");

		WeatherModel weatherModel = new WeatherModel();
		weatherModel.setIcon(icon.asText());
		weatherModel.setTemperature(temperature.asText());
		weatherModel.setDesCode(desCode.asText());
		weatherModel.setWindSpd(windSpd.asText());
		weatherModel.setPop(pop.asText());
		return weatherModel;
	}

	public List<WeatherModel> onWeekWeather(String jsonWeather) {
		final int WEEK = 7;
		List<WeatherModel> weatherOnWeek = new ArrayList<>();
		JsonNode treeJson = readTree(jsonWeather);
		JsonNode data = treeJson.findValue("data");
		for (int i = 0; i < WEEK; i++) {
			JsonNode firstDay = data.get(i);
			JsonNode temperature = firstDay.findValue("temp");
			JsonNode icon = firstDay.findValue("icon");
			JsonNode desCode = firstDay.findValue("code");
			JsonNode windSpd = firstDay.findValue("wind_spd");
			JsonNode pop = firstDay.findValue("pop");
			JsonNode datetime = firstDay.findValue("datetime");

			WeatherModel weatherModel = new WeatherModel();
			weatherModel.setIcon(icon.asText());
			weatherModel.setTemperature(temperature.asText());
			weatherModel.setDesCode(desCode.asText());
			weatherModel.setWindSpd(windSpd.asText());
			weatherModel.setPop(pop.asText());
			weatherModel.setDatetime(datetime.asText());
			weatherOnWeek.add(weatherModel);
		}
		return weatherOnWeek;

	}

}
