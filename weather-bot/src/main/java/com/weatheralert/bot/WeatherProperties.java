package com.weatheralert.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.starter.SpringWebhookBot;

/*
 * Bot properties 
 */
@PropertySource({ "classpath:secrets.properties" })
abstract class WeatherProperties extends SpringWebhookBot {

	@Value("${telegram.bot.name}")
	private String botName;

	@Value("${telegram.bot.key}")
	private String token;

	@Value("${telegram.webhook-path}")
	private String path;

	public WeatherProperties(SetWebhook setWebhook) {
		super(setWebhook);
	}

	@Override
	public String getBotPath() {
		return path;
	}

	@Override
	public String getBotUsername() {
		return botName;
	}

	@Override
	public String getBotToken() {
		return token;
	}
}
