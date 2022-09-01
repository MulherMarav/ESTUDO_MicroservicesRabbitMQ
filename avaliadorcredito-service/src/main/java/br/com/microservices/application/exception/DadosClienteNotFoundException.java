package br.com.microservices.application.exception;

public class DadosClienteNotFoundException extends Exception { //exception checada

	private static final long serialVersionUID = 1L;

	public DadosClienteNotFoundException() {
		super("Dados do cliente não encontrado para o CPF informado!");
	}
}
