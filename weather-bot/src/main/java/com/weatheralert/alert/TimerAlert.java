package com.weatheralert.alert;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.weatheralert.bot.WeatherBot;
import com.weatheralert.commands.Command;
import com.weatheralert.model.TelegramUser;
import com.weatheralert.repository.UserRepository;
import com.weatheralert.router.CallbackRouter;

/*
 * Responsible for sending weather alerts
 */
@Component
public class TimerAlert {

	@Autowired
	private WeatherBot bot;

	@Autowired
	private CallbackRouter callbackRouter;

	@Autowired
	private TimeStore timeStore;

	@Autowired
	private UserRepository userRepository;

	@Async
	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
	public void scheduledAlert() {
		int minuteOfDay = timeStore.isAlertTime();
		if (minuteOfDay != 0) {
			sendAlert(minuteOfDay);
		}
	}

	private void sendAlert(int minuteOfDay) {
		List<TelegramUser> users = userRepository.findByAlertMinutes(String.valueOf(minuteOfDay));
		users.forEach(user -> {
			Update update = getUpdate(user);
			execute(callbackRouter.callbackRoute(update));

		});
	}

	private Update getUpdate(TelegramUser user) {
		Long chatId = user.getChatId();
		Chat chat = new Chat();
		chat.setId(chatId);
		Message message = new Message();
		message.setChat(chat);
		CallbackQuery callbackQuery = new CallbackQuery();
		callbackQuery.setMessage(message);
		callbackQuery.setData(Command.SEND_ALERT.getName());
		Update update = new Update();
		update.setCallbackQuery(callbackQuery);
		return update;
	}

	private void execute(BotApiMethod<?> botApiMethod) {
		try {
			bot.execute(botApiMethod);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
