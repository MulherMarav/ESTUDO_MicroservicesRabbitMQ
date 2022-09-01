package br.com.microservices.infra.mqqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.microservices.domain.model.Cartao;
import br.com.microservices.domain.model.ClienteCartao;
import br.com.microservices.domain.model.DadosSolicitacaoEmissaoCartao;
import br.com.microservices.infra.repository.CartaoRepository;
import br.com.microservices.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {

	private final CartaoRepository cartaoRepository;
	
	private final ClienteCartaoRepository clienteCartaoRepository;
	
	@RabbitListener(queues = "${mq.queues.emissao-cartoes}") //vai escutar a fila
	
	//listener que escuta a fila		  //o payload enviado via protocolo HTTP a partir do endpoint REST
	public void receberSolicitacaoEmissao(@Payload String payLoad) { // conteúdo JSON enviado por um meio de transporte
		//desserializar
		
		try {
			
			var mapper = new ObjectMapper();
			DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payLoad, DadosSolicitacaoEmissaoCartao.class);
			
			Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow(); //se não achar lança exception
			
			ClienteCartao clienteCartao = new ClienteCartao();
			
			clienteCartao.setCartao(cartao);
			clienteCartao.setCpf(dados.getCpf());
			clienteCartao.setLimite(dados.getLimiteLiberado());
			
			clienteCartaoRepository.save(clienteCartao);
			
		} catch (Exception e) {
			e.printStackTrace();
		} //se o json vim errado entra na exception
	}
}
