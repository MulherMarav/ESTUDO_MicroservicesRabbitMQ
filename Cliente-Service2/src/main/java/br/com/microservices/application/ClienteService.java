package br.com.microservices.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.microservices.domain.model.Cliente;
import br.com.microservices.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //vai instanciar os atributos no construtor
public class ClienteService {
	
	//final -> não pode ser instanciada
	private final ClienteRepository repository;
	
	@Transactional
	public Cliente save(Cliente cliente) {
		return repository.save(cliente);
	}

	public Optional<Cliente> getByCpf(String cpf){
		return repository.findByCpf(cpf);
	}
}
