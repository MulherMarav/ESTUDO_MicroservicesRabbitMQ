package br.com.microservices.application;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.microservices.domain.model.ClienteCartao;
import br.com.microservices.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {
	
	private final ClienteCartaoRepository repository;
	
	public List<ClienteCartao> listCartoesByCpf(String cpf){
		return repository.findByCpf(cpf);
	}
}
