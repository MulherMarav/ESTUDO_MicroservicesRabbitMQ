package br.com.microservices.infra.mqqueue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.microservices.domain.model.DadosSolicitacaoEmissaoCartao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartaoPublisher {

	private final RabbitTemplate rabbitTemplate;
	private final Queue queueEmissaoCartoes; //recebe as infos do MQConfig
	
	public void solicitarCartao(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
		var json = convertInToJson(dados);
		rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), json); 
		//qual é a fila e a mensagem, ai ele publica
	}
	
	private String convertInToJson(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		var json = mapper.writeValueAsString(dados);
	
		return json;
	}
}
