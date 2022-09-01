package br.com.microservices.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservices.application.exception.DadosClienteNotFoundException;
import br.com.microservices.application.exception.ErroComunicacaoMicroservicesException;
import br.com.microservices.application.exception.ErroSolicitacaoCartaoException;
import br.com.microservices.domain.model.DadosAvaliacao;
import br.com.microservices.domain.model.DadosSolicitacaoEmissaoCartao;
import br.com.microservices.domain.model.ProtocoloSolicitacaoCartao;
import br.com.microservices.domain.model.RetornoAvaliacaoCliente;
import br.com.microservices.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController { // é considerado usuario final, por isso não é resource

	private final AvaliadorCreditoService service;

	@GetMapping
	public String status() {
		return "ok";
	}

	@GetMapping(value = "/situacao-cliente", params = "cpf")
	public ResponseEntity<?> consultarSituacaoCliente(@RequestParam("cpf") String cpf) {
		try {

			SituacaoCliente situacaoCliente = service.obterSituacaoCliente(cpf);

			return ResponseEntity.ok(situacaoCliente);

		} catch (DadosClienteNotFoundException e) {

			return ResponseEntity.notFound().build();

		} catch (ErroComunicacaoMicroservicesException e) {

			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> realizarAvaliacao(@RequestBody DadosAvaliacao dados) {
		try {

			RetornoAvaliacaoCliente retornoAvaliacaoCliente = service.realizarAvaliacao(dados.getCpf(),
					dados.getRenda());

			return ResponseEntity.ok(retornoAvaliacaoCliente);

		} catch (DadosClienteNotFoundException e) {

			return ResponseEntity.notFound().build();

		} catch (ErroComunicacaoMicroservicesException e) {

			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
	
	@PostMapping("/solicitacoes-cartao")
	public ResponseEntity<?> solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
		try {
			
			 ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = 
					 service.solicitarEmissaoCartao(dados);
			 
			 return ResponseEntity.ok(protocoloSolicitacaoCartao);
			 
		} catch (ErroSolicitacaoCartaoException e) {
			
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
