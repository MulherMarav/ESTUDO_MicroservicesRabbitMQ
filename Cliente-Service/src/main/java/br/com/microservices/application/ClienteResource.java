package br.com.microservices.application;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.microservices.application.representation.ClienteSaveRequest;
import br.com.microservices.domain.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteResource {
	
	//final -> não pode ser instanciada
	private final ClienteService service;

	@GetMapping
	public String status() {
		log.info("Obtendo o status do microservice cliente");
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody ClienteSaveRequest request) {
		
		Cliente cliente = request.toModel();
		service.save(cliente);
		
		URI headerLocation = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.query("cpf={cpf}")
				.buildAndExpand(cliente.getCpf())
				.toUri();
	
		//http://localhost:PORT/clientes?cpf=...
				
		return ResponseEntity.created(headerLocation).build(); //201, específico para POST
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<Cliente> dadosCliente(@RequestParam("cpf") String cpf){
		Optional<Cliente> cliente = service.getByCpf(cpf);
		
		if(cliente.isEmpty()) return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(cliente.get());
	}
}
