package com.weatheralert.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.weatheralert.bpp.annotation.CommandHandlerMethod;
import com.weatheralert.commands.Command;
import com.weatheralert.handler.CommandHandler;
import com.weatheralert.templates.AnswerTemplate;

@Component
public class ErrorsHandler implements CommandHandler {

	@Autowired
	private AnswerTemplate answerTemplate;

	/**
	 * Handler for {@link Command.ERROR}
	 */
	@CommandHandlerMethod(Command.ERROR)
	private SendMessage handleError(Message message) {
		return answerTemplate.getMessage(message, "У данной команды нет поддержки");
	}

}
