# Project service-weather
Weather cities

1. Crear contenedor db
  - Ir a la ruta service-weather/src/test/docker
  - Ejecutar el comando:
      docker-compose up
	  
	  
2. Compilar proyecto
   - En la ruta raíz del proyecto, ejecutar el comando:
      mvn clean package

3. Crear contenedor del servicio
   - En la raíz del proyecto, ejecutar el comando:
       docker-compose up --build
	   
4. Ejecución del proyecto
   - Acceder a los endpoints del servicio con la siguientes rutas:
     - Obtener el clicma de la ciudad:
	    http://localhost:8081/weather?city={city}
		
		Ejemplo:
		   http://localhost:8081/weather?city=Nagasaki
	- Obtener las ciudades consultadas:
	    http://localhost:8081/weather/cities
