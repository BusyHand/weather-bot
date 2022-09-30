package com.weatheralert.handler.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import com.weatheralert.bpp.annotation.CommandHandlerMethod;
import com.weatheralert.commands.Command;
import com.weatheralert.handler.CommandHandler;
import com.weatheralert.model.TelegramUser;
import com.weatheralert.model.WeatherModel;
import com.weatheralert.repository.UserRepository;
import com.weatheralert.service.WeatherFinder;
import com.weatheralert.templates.AnswerTemplate;
import com.weatheralert.templates.WeatherTextTemplate;

/**
 * Handler that takes arguments of the {@link CallbackQuery}
 */
@Component
public class WeatherCallbackHandler implements CommandHandler {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WeatherFinder weatherSender;

	@Autowired
	private WeatherTextTemplate textTemplate;

	@Autowired
	private AnswerTemplate answerTemplate;

	/**
	 * Handler for {@link Command.ON_WEEK}
	 */
	@CommandHandlerMethod(Command.ON_WEEK)
	private SendMessage onWeekMessage(CallbackQuery callbackQuery) {
		Long chatId = callbackQuery.getMessage().getChatId();
		Optional<TelegramUser> user = userRepository.getByChatId(chatId);
		if (user.isEmpty()) {
			return answerTemplate.getMessage(callbackQuery.getMessage(), "Cначала нужно выбрать локацию");
		}
		TelegramUser telegramUser = user.get();

		List<WeatherModel> forecastOnWeek = weatherSender.onWeekWeather(telegramUser.getLocation());
		return answerTemplate.getMenuWeatherFeatures(callbackQuery.getMessage(),
				textTemplate.getOnWeek(forecastOnWeek));
	}

	/**
	 * Handler for {@link Command.CURRENT}
	 */
	@CommandHandlerMethod(Command.CURRENT)
	protected SendMessage currentWeatherMessage(CallbackQuery callbackQuery) {
		Long chatId = callbackQuery.getMessage().getChatId();
		Optional<TelegramUser> user = userRepository.getByChatId(chatId);
		if (user.isEmpty()) {
			return answerTemplate.getMessage(callbackQuery.getMessage(), "Cначала нужно выбрать локацию");
		}
		TelegramUser telegramUser = user.get();

		WeatherModel currentWeatherModel = weatherSender.currentWeather(telegramUser.getLocation());
		return answerTemplate.getMenuWeatherFeatures(callbackQuery.getMessage(),
				textTemplate.getCurrent(currentWeatherModel));

	}

	/**
	 * Handler for {@link Command.ON_DAY}
	 */
	@CommandHandlerMethod(Command.ON_DAY)
	protected SendMessage forecastOnDayMessage(CallbackQuery callbackQuery) {
		Long chatId = callbackQuery.getMessage().getChatId();
		Optional<TelegramUser> user = userRepository.getByChatId(chatId);
		if (user.isEmpty()) {
			return answerTemplate.getMessage(callbackQuery.getMessage(), "Cначала нужно выбрать локацию");
		}
		TelegramUser telegramUser = user.get();

		WeatherModel forecastWeatherModel = weatherSender.onDayWeather(telegramUser.getLocation());
		return answerTemplate.getMenuWeatherFeatures(callbackQuery.getMessage(),
				textTemplate.getOnDay(forecastWeatherModel));
	}

	/**
	 * Handler for {@link Command.SEND_ALERT}
	 */
	@CommandHandlerMethod(Command.SEND_ALERT)
	private SendMessage weatherAlertMessage(CallbackQuery callbackQuery) {
		Long chatId = callbackQuery.getMessage().getChatId();
		Optional<TelegramUser> user = userRepository.getByChatId(chatId);
		if (user.isEmpty()) {
			return answerTemplate.getMessage(callbackQuery.getMessage(), "Cначала нужно выбрать локацию");
		}
		TelegramUser telegramUser = user.get();

		WeatherModel forecastWeatherModel = weatherSender.onDayWeather(telegramUser.getLocation());
		WeatherModel currentWeatherModel = weatherSender.currentWeather(telegramUser.getLocation());
		return answerTemplate.getMenuWeatherFeatures(callbackQuery.getMessage(),
				textTemplate.getForAlert(forecastWeatherModel, currentWeatherModel));
	}

}
