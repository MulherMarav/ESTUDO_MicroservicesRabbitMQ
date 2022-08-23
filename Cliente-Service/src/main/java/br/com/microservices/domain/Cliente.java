package br.com.microservices.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data /* Gera: Getter, Setter, RequiredArgsConstructor, ToString e EqualsAndHashCode */
@NoArgsConstructor //necessário por conta da JPA
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String cpf;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private Integer idade;
	
	//para receber cliente na requisição e criar cliente novo
	public Cliente(String cpf, String nome, Integer idade) {
		this.cpf = cpf;
		this.nome = nome;
		this.idade = idade;
	}
}
