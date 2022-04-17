package com.demo.weather.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "WEATHER_CITIES")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityWeatherEntity {
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "NUMBER_REQUEST")
	private Integer count;
	
	@Column(name = "LAST_REQUEST", columnDefinition = "DATETIME")
	private LocalDateTime lastRequest;
	
	@Column(name = "REQUEST", columnDefinition = "TEXT")
	private String request;
	
}
