package br.com.microservices.application.representation;

import java.math.BigDecimal;

import br.com.microservices.domain.BandeiraCartao;
import br.com.microservices.domain.Cartao;
import lombok.Data;

@Data
public class CartaoSaveRequest {

	private String nome;
	private BandeiraCartao bandeira;
	private BigDecimal renda;
	private BigDecimal limite;
	
	public Cartao toModel() {
		return new Cartao(nome, bandeira, renda, limite);
	}
}
