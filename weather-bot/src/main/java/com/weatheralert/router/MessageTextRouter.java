package com.weatheralert.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.weatheralert.commands.Command;
import com.weatheralert.handler.CommandHandlerFactory;
import com.weatheralert.util.TextValidate;

@Component
public class MessageTextRouter {

	@Autowired
	private CommandHandlerFactory commandHandlerFactory;

	@Autowired
	private TextValidate textValidate;

	public BotApiMethod<?> textRoute(Message message) {
		String inputText = message.getText();
		if (textValidate.isCommand(inputText)) {
			return commandHandlerFactory.handleCommand(message, inputText, true);
		} else {
			return commandHandlerFactory.handleCommand(message, Command.LOCATION_BY_CITY_NAME);
		}
	}
}
