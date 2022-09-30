package com.weatheralert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.weatheralert.bot.WeatherBot;

@RestController
public class WeatherDefaultController {

	@Autowired
	private WeatherBot weatherBot;

	@PostMapping("/")
	public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
		return weatherBot.onWebhookUpdateReceived(update);
	}

}
