package br.com.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}

	// no CMD -> ./mvnw spring-boot:run
}
