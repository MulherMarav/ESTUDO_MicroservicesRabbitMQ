package br.com.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient // naming server
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}

	@Bean //responsável pelo roteamento
	public RouteLocator routes(RouteLocatorBuilder builder) { // injetado pelo spring, lb = load balancer
		return builder
				.routes()
					.route(r -> r.path("/clientes/**").uri("lb://cliente-service"))
					.route(r -> r.path("/cartoes/**").uri("lb://cartao-service"))
					.route(r -> r.path("/avaliacoes-credito/**").uri("lb://avaliadorcredito-service"))
				.build();
	}
}
