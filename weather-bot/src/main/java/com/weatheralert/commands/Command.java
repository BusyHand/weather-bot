package com.weatheralert.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Set of command
 */
@Getter
@RequiredArgsConstructor
public enum Command {
	HELP("/help", true),
	START("/start", true),
	ALERT_TIME("/alert", true), 
	RESET_ALERT("/alert_reset", true), 
	STOP("/stop", true), 
	LOCATION("Location", false),
	LOCATION_BY_CITY_NAME("By city name", false),
	LOCATION_FROM_MESSAGE("Location from message", false),
	SEND_ALERT("Send alert", false),
	CURRENT("Current", false),
	ON_DAY("Day", false),
	ON_WEEK("Week", false),
	ERROR("Error", false);


	private final String name;
	private final boolean isLineCommand;

}