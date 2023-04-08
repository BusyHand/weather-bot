package com.weatheralert.handler.impl;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.weatheralert.alert.TimeStore;
import com.weatheralert.bpp.annotation.CommandHandlerMethod;
import com.weatheralert.commands.Command;
import com.weatheralert.handler.CommandHandler;
import com.weatheralert.model.TelegramUser;
import com.weatheralert.repository.UserRepository;
import com.weatheralert.service.LocationFinder;
import com.weatheralert.templates.AnswerTemplate;
import com.weatheralert.util.TextParser;
import com.weatheralert.util.TextValidate;

@Component
public class AlertHandler implements CommandHandler {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LocationFinder locationFinder;

	@Autowired
	private TimeStore timeStore;

	@Autowired
	private TextValidate textValidate;

	@Autowired
	private TextParser textParser;

	@Autowired
	private AnswerTemplate answerTemplate;

	/**
	 * Setting alert time to user
	 */
	@CommandHandlerMethod(Command.ALERT_TIME)
	private SendMessage saveUserAlert(Message message) {
		String text = message.getText();
		String inputTime = text.substring(Command.ALERT_TIME.getName().length()).trim();
		if (inputTime.isEmpty() || !textValidate.isValidTime(inputTime))
			return answerTemplate.getMessage(message, "❌ Время указывается в формате: /alert 00:00");

		List<Integer> timeFromText = textParser.getTimeFromText(inputTime);
		Integer hours = timeFromText.get(0);
		Integer minutes = timeFromText.get(1);
		if (!textValidate.isValidTime(hours, minutes))
			return answerTemplate.getMessage(message, "❌ Такого времени не существует");

		Optional<TelegramUser> userOptional = userRepository.getByChatId(message.getChatId());
		if (userOptional.isEmpty()) {
			return answerTemplate.getMessage(message, "❌ Сначала нужно выбрать локацию");
		}

		TelegramUser user = userOptional.get();
		ZoneId userTimeZone = locationFinder.findZoneTimeByLocation(user.getLocation());
		int minutesOfDay = timeStore.addTime(userTimeZone, hours, minutes);
		user.setAlertMinutes(minutesOfDay);
		userRepository.save(user);

		String strHours = hours < 10 ? "0" + hours : hours + "";
		String strMinutes = minutes < 10 ? "0" + minutes : minutes + "";
		return answerTemplate.getMessage(message, "✔️ Время уведомления установлено на: " + strHours + ":"
				+ strMinutes);
	}

	@CommandHandlerMethod(Command.RESET_ALERT)
	private SendMessage handleResetAlert(Message message) {
		Optional<TelegramUser> user = userRepository.getByChatId(message.getChatId());
		if (user.isEmpty()) {
			return answerTemplate.getMessage(message, "❌ Сначала нужно установить время");
		}
		TelegramUser telegramUser = user.get();
		telegramUser.setAlertMinutes("");
		userRepository.save(telegramUser);
		return answerTemplate.getMessage(message, "✔️ Время оповещения сброшено");
	}

}
