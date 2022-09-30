package com.weatheralert.model;

import lombok.Data;

@Data
public class TownView {
	
	private String id;

	private String city_name;

	private String country;

	private String region;

	private String latitude;

	private String longitude;
	
	@Override
	public String toString() {
		return city_name + " " + region + " " + country;
	}

	public void addOnIndexField(int index, String fieldValue) {
		switch (index) {
		case 0:
			id = fieldValue;
			break;
		case 1:
			city_name = fieldValue;
			break;
		case 2:
			country = fieldValue;
			break;
		case 3:
			region = fieldValue;
			break;
		case 4:
			latitude = fieldValue;
			break;
		case 5:
			longitude = fieldValue;
			break;
		default:
			return;
		}
	}

}
