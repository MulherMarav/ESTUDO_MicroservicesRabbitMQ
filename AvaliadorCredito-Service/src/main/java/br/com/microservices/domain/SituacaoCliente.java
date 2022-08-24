package br.com.microservices.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //criação de objeto do tipo situacao cliente
public class SituacaoCliente {
	
	private DadosCliente cliente;
	private List<CartaoCliente> cartoes;

}
