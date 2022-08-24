package br.com.microservices.infra.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.microservices.domain.CartaoCliente;

@FeignClient(value = "cartao-service", path = "/cartoes") //client http, não precisa da URL por conta do load balancer
public interface CartaoResourceClient {
	
	@GetMapping(params = "cpf") //necessário tipar o retorno
	public ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);

	//por padrao os métodos são publicos
}
