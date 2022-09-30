package com.weatheralert.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class TextParser {

	public String getCityNameFromText(String text) {
		Pattern pattern = Pattern.compile("[а-яА-Я\\s-]+");
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			return matcher.group();
		}
		else return "";
	}
	
	public List<Integer> getTimeFromText(String text) {
		String time = getTimeSubtring(text);
		String[] strs = time.split(":");
		Integer hours = Integer.valueOf(strs[0]);
		Integer minutes = Integer.valueOf(strs[1]);
		return List.of(hours, minutes);
	}
	
	private String getTimeSubtring(String time) {
		Pattern pattern = Pattern.compile("\\d?\\d:\\d\\d?");
		Matcher matcher1 = pattern.matcher(time);

		if (matcher1.find()) {
			return matcher1.group();
		} else {
			return ":";
		}
	}



}
