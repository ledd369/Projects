package com.demo.weather.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.demo.weather.components.WeatherCityResponse;
import com.demo.weather.repository.WeatherCitiesRepository;

@ExtendWith(MockitoExtension.class)
class CitiesWeatherServiceTests {
	
	@Mock
	RestTemplate restTemplate;
	
	@Mock
	WeatherCitiesRepository repository;
	
	@InjectMocks
	CitiesWeatherService service;
	
	@Test
	void getCityWeather() {
		Mockito.when(restTemplate.exchange(
				Mockito.anyString(),
				Mockito.eq(HttpMethod.GET),
				Mockito.any(),
				Mockito.<Class<String>>any()
				)).thenReturn(ResponseEntity.ok("response"));
		
		Mockito.when(repository.findByCity(Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(repository.save(Mockito.any())).thenReturn(null);
		String resp = service.getWeather("Mexico city");
		
		assertTrue(resp.equals("response"));
	}
	
	@Test
	void getCities() {
		
		Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(Mockito.mock(Page.class));
		WeatherCityResponse resp = service.getCities(0,10);
		
		assertNotNull(resp);
	}

}
