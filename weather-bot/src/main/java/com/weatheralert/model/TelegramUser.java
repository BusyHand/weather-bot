package com.weatheralert.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TelegramUser {

	@Id
	@EqualsAndHashCode.Exclude
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;

	private Long chatId;

	private String alertMinutes;
	
	@Embedded
	private EmbededLocation location;
	
	@Tolerate
	public void setAlertMinutes(Integer alertMinutes) {
		this.alertMinutes = String.valueOf(alertMinutes);
	}

}
