package com.weatheralert.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.weatheralert.bpp.annotation.CommandHandlerMethod;
import com.weatheralert.commands.Command;
import com.weatheralert.handler.CommandHandler;
import com.weatheralert.model.EmbededLocation;
import com.weatheralert.model.TelegramUser;
import com.weatheralert.repository.UserRepository;
import com.weatheralert.templates.AnswerTemplate;

/**
 * Handler class for command that use {@link CallbackQuery} as argument
 */
@Component
public class LocationCallbackHandler implements CommandHandler {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AnswerTemplate answerTemplate;

	/**
	 * Callback handler that invoke after {@link Command.LOCATION_BY_CITY_NAME}
	 */
	@CommandHandlerMethod(Command.LOCATION)
	private SendMessage handleLocationCallback(CallbackQuery callback) {
		EmbededLocation location = parseLocation(callback.getData());
		saveLocation(callback.getMessage().getChatId(), location);
		return answerTemplate.getMenuWeatherFeatures(callback.getMessage());
	}

	private EmbededLocation parseLocation(String location) {
		String[] dataLocation = location.split(",");
		EmbededLocation emLocation = new EmbededLocation(dataLocation[1], dataLocation[2]);
		return emLocation;
	}

	private void saveLocation(Long chatId, EmbededLocation location) {
		TelegramUser user = userRepository.getByChatId(chatId)
				.orElse(new TelegramUser());
		user.setChatId(chatId);
		user.setLocation(location);
		userRepository.save(user);
	}

}
