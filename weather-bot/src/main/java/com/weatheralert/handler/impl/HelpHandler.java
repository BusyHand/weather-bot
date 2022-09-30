package com.weatheralert.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.weatheralert.bpp.annotation.CommandHandlerMethod;
import com.weatheralert.commands.Command;
import com.weatheralert.handler.CommandHandler;
import com.weatheralert.repository.UserRepository;
import com.weatheralert.templates.AnswerTemplate;

@Component
public class HelpHandler implements CommandHandler {

	@Autowired
	private AnswerTemplate answerTemplate;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Handler for {@link Command.HELP}
	 */
	@CommandHandlerMethod(Command.HELP)
	private SendMessage handleError(Message message) {
		return answerTemplate.getMessage(message,
				"Weather bot - это удобный телеграмм бот с возможностью узнать погоду и установить время оповещения пользователя\r\n"
						+
						"/help - список команд\r\n" +
						"/alert (00:00) - установка времени оповещения с актуальным прогнозом погоды. Для установки нужно отправить сообщение вида: /alert 23:12");
	}

	@CommandHandlerMethod(Command.START)
	private SendMessage handleStart(Message message) {
		return answerTemplate.getLocationReplyMarkup(message,
				"Напишите название города\r\n" +
						"/help - список команд");
	}

	@CommandHandlerMethod(Command.STOP)
	private SendMessage handleStop(Message message) {
		userRepository.deleteByChatId(message.getChatId());
		return answerTemplate.getLocationReplyMarkup(message,
				"Данные удалены");
	}

}
