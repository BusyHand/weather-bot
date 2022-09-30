package com.weatheralert.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@EnableAsync
@EnableScheduling
public class AppConfiguration {
	
	@Bean
	public SetWebhook setWebhook() {
		return new SetWebhook();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	


}
