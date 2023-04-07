package com.weatheralert.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.weatheralert.bpp.annotation.CommandHandlerMethod;
import com.weatheralert.commands.Command;
import com.weatheralert.handler.CommandHandler;
import com.weatheralert.model.EmbededLocation;
import com.weatheralert.model.TelegramUser;
import com.weatheralert.model.TownView;
import com.weatheralert.repository.UserRepository;
import com.weatheralert.service.LocationFinder;
import com.weatheralert.templates.AnswerTemplate;
import com.weatheralert.util.TextParser;

@Component
public class LocationHandler implements CommandHandler {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LocationFinder locationDataFinder;

	@Autowired
	private TextParser textParser;

	@Autowired
	private AnswerTemplate answerTemplate;

	/**
	 * The {@link Command.LOCATION_BY_CITY_NAME} handler, which returns a list of
	 * cities found by the user input
	 */
	@CommandHandlerMethod(Command.LOCATION_BY_CITY_NAME)
	private SendMessage findCityByName(Message message) {
		String cityName = textParser.getCityNameFromText(message.getText());
		if (!cityName.isBlank()) {
			List<TownView> findTowns = locationDataFinder.findTownsByName(cityName);
			if (!findTowns.isEmpty()) {
				List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
				findTowns.forEach(townView -> {
					buttons.add(List.of(
							InlineKeyboardButton.builder()
									.text(townView.getCity_name() + ", " + townView.getRegion())
									.callbackData(Command.LOCATION.getName() + "," +
											townView.getLatitude() + "," +
											townView.getLongitude())
									.build()));
				});
				return answerTemplate.getReplyMarkup(message, "Выберите город:", buttons);
			} else {
				return answerTemplate.getMessage(message,
						"❌ К сожалению погоду этого города не получается найти. Если вам нужно узнать погоду в том месте в котором вы находитесь, то вы можете отправить свою геолокацию.");
			}
		} else
			return answerTemplate.getMessage(message, "❌ Не удалось прочитать город из текста. Попробуйте еще раз");
	}

	/**
	 * The {@link Command.LOCATION_FROM_MESSAGE} handler that saves the location
	 * data sent by the user using the phone's location exchange feature.
	 */
	@CommandHandlerMethod(Command.LOCATION_FROM_MESSAGE)
	private SendMessage handleLocationReplyMarkup(Message message) {
		EmbededLocation location = parseLocation(message.getLocation());
		saveLocation(message.getChatId(), location);
		return answerTemplate.getMenuWeatherFeatures(message);
	}

	private EmbededLocation parseLocation(Location location) {
		EmbededLocation emLocation = new EmbededLocation(location.getLatitude(), location.getLongitude());
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
