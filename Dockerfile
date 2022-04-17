FROM openjdk:8

ADD target/service-weather-1.0.0.jar service-weather.jar

ENTRYPOINT ["java", "-jar", "service-weather.jar"]