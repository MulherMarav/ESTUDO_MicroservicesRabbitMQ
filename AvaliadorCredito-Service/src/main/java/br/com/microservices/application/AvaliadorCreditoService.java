package br.com.microservices.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.microservices.application.exception.DadosClienteNotFoundException;
import br.com.microservices.application.exception.ErroComunicacaoMicroservicesException;
import br.com.microservices.domain.CartaoCliente;
import br.com.microservices.domain.DadosCliente;
import br.com.microservices.domain.SituacaoCliente;
import br.com.microservices.infra.clients.CartaoResourceClient;
import br.com.microservices.infra.clients.ClienteResourceClient;
import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceClient clienteClient;
	
	private final CartaoResourceClient cartaoClient;
	
	public SituacaoCliente obterSituacaoCliente(String cpf) 
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		
		try {
			
			ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
			ResponseEntity<List<CartaoCliente>> cartoesClienteResponse = cartaoClient.getCartoesByCliente(cpf);
			
			return SituacaoCliente.builder()
					.cliente(dadosClienteResponse.getBody())
					.cartoes(cartoesClienteResponse.getBody())
					.build();
			
		} catch (FeignClientException e) {
			int status = e.status();
			
			//erro 404
			if(HttpStatus.NOT_FOUND.value() == status) throw new DadosClienteNotFoundException();
			
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
		

	}

}
