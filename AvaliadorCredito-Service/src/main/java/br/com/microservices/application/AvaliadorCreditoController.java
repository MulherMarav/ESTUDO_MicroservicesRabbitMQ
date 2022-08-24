package br.com.microservices.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservices.application.exception.DadosClienteNotFoundException;
import br.com.microservices.application.exception.ErroComunicacaoMicroservicesException;
import br.com.microservices.domain.SituacaoCliente;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController { //é considerado usuario final, por isso não é resource 

	private final AvaliadorCreditoService service;
	
	@GetMapping
	public String status() {
		return "ok";
	}
	
	@GetMapping(value = "/situacao-cliente", params = "cpf")
	public ResponseEntity<?> consultaSituacaoCliente(@RequestParam("cpf") String cpf){
		try {
			
			SituacaoCliente situacaoCliente = service.obterSituacaoCliente(cpf);
			
			return ResponseEntity.ok(situacaoCliente);
			
		} catch (DadosClienteNotFoundException e) {
			
			return ResponseEntity.notFound().build();
			
		} catch (ErroComunicacaoMicroservicesException e) {

			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
}
