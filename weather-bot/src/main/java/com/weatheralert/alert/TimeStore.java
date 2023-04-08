package com.weatheralert.alert;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.weatheralert.model.TelegramUser;
import com.weatheralert.repository.UserRepository;

/*
 * Stores a set of existing minutes to improve performance through fewer database connections
 */
@Configuration
public class TimeStore {

	@Autowired
	private UserRepository userRepository;

	private final Set<Integer> minuteOfDaySet = ConcurrentHashMap.newKeySet();

	@Bean
	CommandLineRunner fillMinutesInSet() {
		return willDo -> {
			List<TelegramUser> allUsers = userRepository.findAll();
			List<Integer> minutesList = allUsers.stream()
					.map((TelegramUser::getAlertMinutes))
					.map(Integer::valueOf)
					.collect(toList());
			minuteOfDaySet.addAll(minutesList);
		};
	}

	public int addTime(ZoneId userTimeZone, Integer hours, Integer minutes) {
		ZoneId defaultZone = ZoneId.of("GMT");
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.of(hours, minutes);
		LocalDateTime alertTime = LocalDateTime.of(date, time);
		ZonedDateTime alertTimeZone = ZonedDateTime.of(alertTime, userTimeZone);
		ZonedDateTime alertTimeDefaultZone = alertTimeZone.withZoneSameInstant(defaultZone);
		int minutesOfDay = alertTimeDefaultZone.get(ChronoField.MINUTE_OF_DAY);
		minuteOfDaySet.add(minutesOfDay);
		return minutesOfDay;

	}

	public int isAlertTime() {
		ZoneId defaultZone = ZoneId.of("GMT");
		LocalDateTime currentTime = LocalDateTime.now(defaultZone);
		ZonedDateTime currentTimeZone = ZonedDateTime.of(currentTime, defaultZone);
		int minutesOfDay = currentTimeZone.get(ChronoField.MINUTE_OF_DAY);
		if (minuteOfDaySet.contains(minutesOfDay)) {
			return minutesOfDay;
		}
		return 0;
	}

	public Set<Integer> getStoreTime() {
		return Collections.unmodifiableSet(minuteOfDaySet);
	}

}
