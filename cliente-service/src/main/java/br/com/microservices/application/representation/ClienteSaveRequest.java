package br.com.microservices.application.representation;

import br.com.microservices.domain.model.Cliente;
import lombok.Data;

@Data
public class ClienteSaveRequest {

	private String cpf;
	private String nome;
	private Integer idade;
	
	public Cliente toModel() {
		return new Cliente(this.cpf, this.nome, this.idade);
	}
}
