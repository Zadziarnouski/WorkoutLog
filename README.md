# WorkoutLog
![imagename](https://res.cloudinary.com/edmonddantes/image/upload/v1612855390/app_gq5wwp.png)
## About
**WorkoutLog** is a web application that serves as a personal training assistant. The application provides 2 default languages: _English_ and _Russian_. 

With **WL** you can:
* create exercises
* compose a workout
* add body measurement (you can also add a photo)
* view workout and measurement logs
* see the weather
## What's inside
This project is based on the [Spring Boot](https://spring.io/projects/spring-boot "Spring Boot") project and uses these packages :
* [Maven](https://maven.apache.org/ "Maven")
* [Spring Web](https://spring.io/ "Spring Web")
* [Spring Security](https://spring.io/projects/spring-security "Spring Security")
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa "Spring Data JPA") ([Hibernate](http://hibernate.org/ "Hibernate"))
* [Spring Validation](https://spring.io/ "Spring Validation")
* [Thymeleaf](https://www.thymeleaf.org/ "Thymeleaf")
* [PostgreSQL](https://www.postgresql.org/ "PostgreSQL")
* [Cloudinary](https://cloudinary.com/ "Cloudinary")
* [OpenWeatherMap](https://openweathermap.org/ "OpenWeatherMap")
* [ModelMapper](http://modelmapper.org/ "ModelMapper")
* [Log4g2](https://logging.apache.org/log4j/2.x/ "Log4g2")
## Installation
The project is created with Maven, so you just need to import it to your IDE and build the project to resolve the dependencies

```
mvn clean install
```

## Configuration
### Database configuration
Create a PostgreSQL database with the name **pgdb** and add the credentials to ```/resources/application.properties```.
The default ones are :


```Java
spring.datasource.url=jdbc:postgresql://localhost:5432/pgdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Cloudinary configuration
Register on [Cloudinary.com](https://cloudinary.com/ "Cloudinary.com") and add the credentials to /resources/cloudinary.properties. The default ones are :

```Java
cloud.name = XXXXXXXXXXX
api.secret = XXXXXXXXXXXXXXXXXXXXXX
api.key= XXXXXXXXXXXXX
```

### OpenWeatherMap configuration
To get the API key you need to register [OpenWeatherMap.org](https://openweathermap.org/ "OpenWeatherMap.org") https://openweathermap.org/. After add the API key to /resources/owm.properties. The default ones are :

```Java
api.key.owm = XXXXXXXXXXXXXXXX
```

## Usage
Run the project and head out to http://localhost:8080

## Author
Send me message to [Gmail](mailto:taras.zadziarnouski@gmail.com "Gmail")

Add me to [LinkedIn](https://www.linkedin.com/in/taras-zadziarnouski-b6205a206/ "LinkedIn")

Follow me to [Instagram](https://t.me/taraszadziarnouski "Instagram")
