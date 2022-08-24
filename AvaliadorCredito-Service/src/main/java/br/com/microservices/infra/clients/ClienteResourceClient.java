package br.com.microservices.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.microservices.domain.DadosCliente;

@FeignClient(value = "cliente-service", path = "/clientes") //client http, não precisa da URL por conta do load balancer
public interface ClienteResourceClient {
	
	@GetMapping(params = "cpf") //necessário tipar o retorno
	public ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);

	//por padrao os métodos são publicos
}
