package com.demo.weather.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.weather.components.CityResponse;
import com.demo.weather.components.WeatherCityResponse;
import com.demo.weather.model.CityWeatherEntity;
import com.demo.weather.repository.WeatherCitiesRepository;

@Service
public class CitiesWeatherService {
	
	@Autowired
	private WeatherCitiesRepository repository;
	
	RestTemplate restTemplate = new RestTemplate();
	
	@Value("${weather.city.appid}")
	String appid;
	
	public String getWeather(String city) {
		
		String weatherCityUrl = "https://api.openweathermap.org/data/2.5/weather";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(weatherCityUrl)
				.queryParam("q", city)
				.queryParam("appid", appid)
				.build().toUriString();
		
		String weatherResponse = "";
		Optional<CityWeatherEntity> cityWeather = Optional.empty();
		try {
			cityWeather = repository.findByCity(city);
			weatherResponse = restTemplate.exchange(urlTemplate, HttpMethod.GET, null, String.class).getBody();
			repository.save(buildCityEntity(city, cityWeather, weatherResponse));
		} catch(HttpClientErrorException he) {
			throw he;
		} catch(Exception e) {
			if(cityWeather.isPresent()) {
				weatherResponse = cityWeather.get().getRequest();
			}
		}
		return weatherResponse;
	}
	
	public WeatherCityResponse getCities(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("lastRequest").descending());
		Page<CityWeatherEntity> citiesWeather = repository.findAll(pageable);
		List<CityResponse> cities = new ArrayList<>();
		
		citiesWeather.toList().stream()
		.forEach((city) -> {
			cities.add(
				CityResponse.builder()
				.city(city.getCity())
				.count(city.getCount())
				.build());
		});
		Long total = cities.stream().collect(Collectors.summarizingInt(CityResponse::getCount)).getSum();
		
		return WeatherCityResponse.builder()
				.cities(cities)
				.total(total)
				.build();
	}
	
	private CityWeatherEntity buildCityEntity(String city, Optional<CityWeatherEntity> cityWeather,
			String weatherResponse) {
		
		String idCity = cityWeather.isPresent() ? cityWeather.get().getId() : UUID.randomUUID().toString();
		Integer count = cityWeather.isPresent() ? cityWeather.get().getCount() : 0;
		return CityWeatherEntity.builder()
				.id(idCity)
				.city(city)
				.count(++count)
				.request(weatherResponse)
				.lastRequest(LocalDateTime.now()).build();
	}
}
