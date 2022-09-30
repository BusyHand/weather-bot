package com.weatheralert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class WeatherBotApplicationTests {

	@Test
	void contextLoads() {

		Pattern pattern = Pattern.compile("\\d?\\d:\\d\\d?");
		Matcher matcher1 = pattern.matcher("лфыовдлфы    0:1     длвтатыф");

		if (matcher1.find()) {
			System.out.println(matcher1.group());
		}
	}
}
