package com.demo.weather.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.demo.weather.components.ApiError;
import com.demo.weather.components.WeatherCityResponse;
import com.demo.weather.service.CitiesWeatherService;

@RestController
public class CitiesWeather {
	
	@Autowired
	CitiesWeatherService service;	
	
	@GetMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getWeather(@RequestParam String city) {
		try {
			String weatherResponse = service.getWeather(city);
			if(weatherResponse.isEmpty()) {
				ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error al consultar La ciudad", "Error al consultar la ciudad");
				return new ResponseEntity<Object>(
					      apiError, new HttpHeaders(), apiError.getStatus());
			} else {
				return ResponseEntity.ok(weatherResponse);
			}
		} catch (HttpClientErrorException he) {
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "No se encuentra la ciudad especificada", "Error al consultar la ciudad"); 
			return new ResponseEntity<Object>(
				      apiError, new HttpHeaders(), apiError.getStatus());
		}
	}
	
	@GetMapping("/weather/cities")
	public ResponseEntity<WeatherCityResponse> getCities(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size) {
		return ResponseEntity.ok(service.getCities(page, size));
	}
}
