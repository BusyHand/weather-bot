package com.weatheralert.templates;

import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.weatheralert.model.WeatherModel;

@Component
@PropertySource(value = { "classpath:translate-description.properties" }, encoding = CharEncoding.UTF_8)
public class WeatherTextTemplate {

	@Value("#{${descriptions}}")
	private Map<String, String> descriptionsMap;

	public String getCurrent(WeatherModel weatherView) {
		return descriptionsMap.get(weatherView.getDesCode()) + "\n" +
				"Температура сейчас: " + weatherView.getTemperature() + "°\n" +
				"Скорость ветра: " + weatherView.getWindSpd() + " (m/s)\n";
	}

	public String getOnDay(WeatherModel weatherView) {
		return descriptionsMap.get(weatherView.getDesCode()) + "\n" +
				"Средняя температура на день: " + weatherView.getTemperature() + "°\n" +
				"Вероятность осадков: " + weatherView.getPop() + "%\n" +
				"Средняя cкорость ветра: " + weatherView.getWindSpd() + " (m/s)\n";

	}

	public String getForAlert(WeatherModel weatherViewOnDay, WeatherModel currentWeatherView) {
		return "Погода сейчас:\n" +
				descriptionsMap.get(currentWeatherView.getDesCode()) + "\n" +
				"Температура сейчас: " + currentWeatherView.getTemperature() + "°\n" +
				"Скорость ветра: " + currentWeatherView.getWindSpd() + " (m/s)" +
				"\n---------\n" +
				"Сегодня ожидается:\n" +
				descriptionsMap.get(weatherViewOnDay.getDesCode()) + "\n" +
				"Средняя температура на день: " + weatherViewOnDay.getTemperature() + "°\n" +
				"Вероятность осадков: " + weatherViewOnDay.getPop() + "%\n" +
				"Средняя cкорость ветра: " + weatherViewOnDay.getWindSpd() + " (m/s)\n";
	}

	public String getOnWeek(List<WeatherModel> weatherViewList) {
		return weatherViewList.stream()
				.map(weatherView -> {
					return "---------\n" +
							weatherView.getDatetime() + "\n" +
							descriptionsMap.get(weatherView.getDesCode()) + "\n" +
							"Средняя температура на день: " + weatherView.getTemperature() + "°\n" +
							"Вероятность осадков: " + weatherView.getPop() + "%\n" +
							"Средняя cкорость ветра: " + weatherView.getWindSpd() + " (m/s)\n";
				})
				.collect(joining());
	}

}
