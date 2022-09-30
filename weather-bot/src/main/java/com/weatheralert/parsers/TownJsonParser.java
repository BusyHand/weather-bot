package com.weatheralert.parsers;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.weatheralert.model.TownView;

@Component
public class TownJsonParser extends ParserJson {

	public List<TownView> findTownsByName(String jsonTown) {
		JsonNode treeJson = readTree(jsonTown);
		List<JsonNode> id = treeJson.findValues("wikiDataId");
		List<JsonNode> city = treeJson.findValues("city");
		List<JsonNode> country = treeJson.findValues("country");
		List<JsonNode> region = treeJson.findValues("region");
		List<JsonNode> latitude = treeJson.findValues("latitude");
		List<JsonNode> longitude = treeJson.findValues("longitude");

		List<List<JsonNode>> jsonsTowns = List.of(id, city, country, region, latitude, longitude);
		List<TownView> townsList = new ArrayList<>();
		for (int i = 0; i < jsonsTowns.get(0).size(); i++) {
			TownView townView = new TownView();
			for (int j = 0; j < jsonsTowns.size(); j++) {
				townView.addOnIndexField(j, jsonsTowns.get(j).get(i).asText());
			}
			townsList.add(townView);
		}
		return townsList;
	}

	public ZoneId findZoneTimeByLocation(String jsonTown) {
		JsonNode treeJsonWeather = readTree(jsonTown);
		JsonNode timezone = treeJsonWeather.findValue("timezone");
		return ZoneId.of(timezone.asText());
	}

}
