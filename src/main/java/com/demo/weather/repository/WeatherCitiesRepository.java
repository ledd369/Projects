package com.demo.weather.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.weather.model.CityWeatherEntity;

@Repository
public interface WeatherCitiesRepository extends JpaRepository<CityWeatherEntity, String>{
	public Optional<CityWeatherEntity> findByCity(String city);
}
