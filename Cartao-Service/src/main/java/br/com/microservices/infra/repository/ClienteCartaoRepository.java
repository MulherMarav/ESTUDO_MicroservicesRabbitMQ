package br.com.microservices.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.microservices.domain.ClienteCartao;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {
	
	List<ClienteCartao> findByCpf(String cpf);
}
