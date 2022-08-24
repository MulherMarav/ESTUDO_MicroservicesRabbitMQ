package br.com.microservices.application.exception;

import lombok.Getter;

public class ErroComunicacaoMicroservicesException extends Exception { //exception checada

	private static final long serialVersionUID = 1L;

	@Getter
	private Integer status;
	
	public ErroComunicacaoMicroservicesException(String msg, Integer status) {
		super(msg);
		this.status = status;
	}
}
