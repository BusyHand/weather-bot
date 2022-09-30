package com.weatheralert.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.weatheralert.handler.CommandHandlerFactory;

@Component
public class CallbackRouter {

	@Autowired
	private CommandHandlerFactory сommandHandlerFactory;

	public BotApiMethod<?> callbackRoute(Update update) {
		CallbackQuery callbackQuery = update.getCallbackQuery();
		String[] callbackData = callbackQuery.getData().split(",");
		String commandFromData = callbackData[0];
		return сommandHandlerFactory.handleCommand(callbackQuery, commandFromData, false);

	}

}
