package com.weatheralert.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class TextValidate {

	public boolean isCommand(String text) {
		return text.startsWith("/");
	}

	public boolean isValidTime(String time) {
		Pattern pattern = Pattern.compile("\\d?\\d:\\d\\d?");
		Matcher matcher = pattern.matcher(time);

		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}


	public boolean isValidTime(Integer hours, Integer minutes) {
		if (hours < 0 && hours > 24 || minutes < 0 && minutes > 59)
			return false;
		return true;
	}

}
