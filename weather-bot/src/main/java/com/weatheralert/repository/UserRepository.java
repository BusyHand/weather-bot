package com.weatheralert.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.weatheralert.model.TelegramUser;

public interface UserRepository extends JpaRepository<TelegramUser, Long> {

	public List<TelegramUser> findByAlertMinutes(String minutesOfDay);

	@Transactional
	@Modifying
	public void deleteByChatId(Long chatId);

	public Optional<TelegramUser> getByChatId(Long chatId);

}
