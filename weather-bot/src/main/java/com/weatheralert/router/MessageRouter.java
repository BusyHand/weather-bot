package com.weatheralert.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.weatheralert.commands.Command;
import com.weatheralert.handler.CommandHandlerFactory;
import com.weatheralert.templates.AnswerTemplate;

@Component
public class MessageRouter {

	@Autowired
	private CommandHandlerFactory commandHandlerFactory;

	@Autowired
	private MessageTextRouter messageTextRouter;

	@Autowired
	private AnswerTemplate defaultAnswerSender;

	public BotApiMethod<?> messageRoute(Update update) {
		Message message = update.getMessage();
		if (message.hasText()) {
			return messageTextRouter.textRoute(message);
		} else if (message.hasLocation()) {
			return commandHandlerFactory.handleCommand(message, Command.LOCATION_FROM_MESSAGE);
		} else
			return defaultAnswerSender.getLocationReplyMarkup(message, "/help - список команд");
	}

}
