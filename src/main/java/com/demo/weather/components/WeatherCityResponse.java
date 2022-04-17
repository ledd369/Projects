package com.demo.weather.components;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherCityResponse {
	
	private List<CityResponse> cities;
	private Long total;

}
