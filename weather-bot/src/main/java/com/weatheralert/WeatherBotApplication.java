package com.weatheralert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.mappings.servlet.DispatcherServletMappingDescription;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherBotApplication.class, args);
	}

}
