package com.weatheralert.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
public class EmbededLocation {

	private String latitude;

	private String longitude;

	public EmbededLocation(Double latitude, Double longitude) {
		this.latitude = String.valueOf(latitude);
		this.longitude = String.valueOf(longitude);
	}

}
