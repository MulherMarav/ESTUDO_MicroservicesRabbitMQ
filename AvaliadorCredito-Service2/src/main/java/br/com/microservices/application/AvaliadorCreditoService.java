package br.com.microservices.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.microservices.application.exception.DadosClienteNotFoundException;
import br.com.microservices.application.exception.ErroComunicacaoMicroservicesException;
import br.com.microservices.application.exception.ErroSolicitacaoCartaoException;
import br.com.microservices.domain.model.Cartao;
import br.com.microservices.domain.model.CartaoAprovado;
import br.com.microservices.domain.model.CartaoCliente;
import br.com.microservices.domain.model.DadosCliente;
import br.com.microservices.domain.model.DadosSolicitacaoEmissaoCartao;
import br.com.microservices.domain.model.ProtocoloSolicitacaoCartao;
import br.com.microservices.domain.model.RetornoAvaliacaoCliente;
import br.com.microservices.domain.model.SituacaoCliente;
import br.com.microservices.infra.clients.CartaoResourceClient;
import br.com.microservices.infra.clients.ClienteResourceClient;
import br.com.microservices.infra.mqqueue.SolicitacaoEmissaoCartaoPublisher;
import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceClient clienteClient;
	
	private final CartaoResourceClient cartaoClient;
	
	private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;
	
	public SituacaoCliente obterSituacaoCliente(String cpf) 
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		
		try {
			
			ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
			ResponseEntity<List<CartaoCliente>> cartoesClienteResponse = cartaoClient.getCartoesByCliente(cpf);
			
			return SituacaoCliente.builder()
					.cliente(dadosClienteResponse.getBody())
					.cartoes(cartoesClienteResponse.getBody())
					.build();
			
		} catch (FeignClientException e) {
			int status = e.status();
			
			//erro 404
			if(HttpStatus.NOT_FOUND.value() == status) throw new DadosClienteNotFoundException();
			
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
	}
	
	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) 
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		
		try {
			
			ResponseEntity<DadosCliente> dadosClienteResponse = clienteClient.dadosCliente(cpf);
			ResponseEntity<List<Cartao>> cartoesResponse = cartaoClient.getCartoesRendaAteh(renda);
			
			DadosCliente dadosCliente = dadosClienteResponse.getBody();
			List<Cartao> cartoes = cartoesResponse.getBody();
			
			var listaCartoesAprovados = cartoes.stream().map(cartao -> {
				CartaoAprovado aprovado = new CartaoAprovado();
				
				aprovado.setCartao(cartao.getNome());
				aprovado.setBandeira(cartao.getBandeira());
				
				BigDecimal limiteBasico = cartao.getLimiteBasico();
				BigDecimal idadeDB = BigDecimal.valueOf(dadosCliente.getIdade());
				
				var fator = idadeDB.divide(BigDecimal.valueOf(10));
				BigDecimal limiteAprovado = fator.multiply(limiteBasico);
				
				aprovado.setLimiteAprovado(limiteAprovado);
				
				return aprovado;
			}).collect(Collectors.toList());
			
			return new RetornoAvaliacaoCliente(listaCartoesAprovados);
			
		} catch (FeignClientException e) {
			int status = e.status();
			
			//erro 404
			if(HttpStatus.NOT_FOUND.value() == status) throw new DadosClienteNotFoundException();
			
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
	}

	public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
		try { 
			
			emissaoCartaoPublisher.solicitarCartao(dados);
			var protocolo = UUID.randomUUID().toString(); //gera hash aleatório
			
			return new ProtocoloSolicitacaoCartao(protocolo);
					
		} catch (Exception e) {
			
			throw new ErroSolicitacaoCartaoException(e.getMessage());
		}
	}
}
