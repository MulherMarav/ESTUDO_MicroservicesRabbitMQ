package br.com.microservices.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

	@Value("${mq.queues.emissao-cartoes}")
	private String emissaoCartoesFila;
	
	@Bean
	public Queue queueEmissaoCartoes(){ //para onde irá publicar as mensagens
		return new Queue(emissaoCartoesFila, true); //nome da fila e se ela é duravél
	}
}
