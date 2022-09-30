package com.weatheralert.model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class WeatherModel {

	@JsonProperty("temp")
	String temperature;

	@JsonProperty("icon")
	String icon;

	@JsonProperty("code")
	String desCode;

	@JsonProperty("wind_spd")
	String windSpd;

	@JsonProperty("pop")
	String pop;

	@JsonProperty("datetime")
	String datetime;

	@JsonProperty("Location")
	EmbededLocation location;

	public void setTemperature(String temperature) {
		Double value = Double.valueOf(temperature);
		DecimalFormat decimalFormat = new DecimalFormat("#");
		String temp = decimalFormat.format(value);
		this.temperature = temp;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setDesCode(String desCode) {
		this.desCode = desCode;
	}

	public void setWindSpd(String windSpd) {
		Double value = Double.valueOf(windSpd);
		DecimalFormat decimalFormat = new DecimalFormat("#");
		String wind = decimalFormat.format(value);
		this.windSpd = wind;
	}

	public void setPop(String pop) {
		this.pop = pop;
	}

	public void setDatetime(String datetime) {
		LocalDate date = LocalDate.parse(datetime);
		int dayOfMonth = date.getDayOfMonth();
		String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("ru"));
		String mouths = date.getMonth().getDisplayName(TextStyle.SHORT, new Locale("ru"));
		String o = dayOfWeek + " " + dayOfMonth + " " + mouths;
		this.datetime = o;
	}

	public void setLocation(EmbededLocation location) {
		this.location = location;
	}

}
