package com.weatheralert.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.weatheralert.router.CallbackRouter;
import com.weatheralert.router.MessageRouter;
import com.weatheralert.templates.AnswerTemplate;

/*
 * Bot that route updates
 */
@Component
public class WeatherBot extends WeatherProperties {

	@Autowired
	private CallbackRouter callbackRouter;

	@Autowired
	private MessageRouter messageRouter;

	@Autowired
	private AnswerTemplate defaultAnswerSender;

	public WeatherBot(SetWebhook setWebhook) {
		super(setWebhook);
	}

	@Override
	public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
		if (update.hasCallbackQuery()) {
			return callbackRouter.callbackRoute(update);
		} else if (update.hasMessage()) {
			return messageRouter.messageRoute(update);
		}
		return defaultAnswerSender.getLocationReplyMarkup(update.getMessage(), "/help - список команд");

	}
}
