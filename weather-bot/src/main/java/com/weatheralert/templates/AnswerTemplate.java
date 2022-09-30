package com.weatheralert.templates;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.weatheralert.commands.Command;

@Component
public class AnswerTemplate {

	private static List<List<InlineKeyboardButton>> getWeatherButtons() {
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		buttons.add(List.of(
				InlineKeyboardButton.builder()
						.text("Текущая погода")
						.callbackData(Command.CURRENT.getName())
						.build(),
				InlineKeyboardButton.builder()
						.text("На день")
						.callbackData(Command.ON_DAY.getName())
						.build(),
				InlineKeyboardButton.builder()
						.text("На неделю")
						.callbackData(Command.ON_WEEK.getName())
						.build()));
		return buttons;
	}

	public SendMessage getMenuWeatherFeatures(Message message) {
		return getMenuWeatherFeatures(message, "Выберите какой прогноз вам нужен");
	}

	public SendMessage getMenuWeatherFeatures(Message message, String text) {
		List<List<InlineKeyboardButton>> buttons = getWeatherButtons();
		return SendMessage.builder()
				.text(text)
				.chatId(message.getChatId())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build();
	}

	public SendMessage getLocationReplyMarkup(Message message, String text) {
		KeyboardRow keyboardRow = new KeyboardRow();
		keyboardRow.add(KeyboardButton.builder().text("Send location").requestLocation(true).build());
		return SendMessage.builder()
				.text(text)
				.chatId(message.getChatId())
				.replyMarkup(ReplyKeyboardMarkup
						.builder()
						.resizeKeyboard(true)
						.keyboardRow(keyboardRow)
						.build()).build();
	}

	public SendMessage getMessage(Message message, String textMessage) {
		return SendMessage.builder()
				.text(textMessage)
				.chatId(message.getChatId())
				.build();
	}

	public SendMessage getReplyMarkup(Message message, String textMessage, List<List<InlineKeyboardButton>> buttons) {
		return SendMessage.builder()
				.text(textMessage)
				.chatId(message.getChatId())
				.replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
				.build();
	}

}
