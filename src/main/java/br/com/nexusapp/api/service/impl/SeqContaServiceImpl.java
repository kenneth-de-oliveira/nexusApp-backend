package br.com.nexusapp.api.service.impl;

import br.com.nexusapp.api.model.Conta;
import br.com.nexusapp.api.model.SeqConta;
import br.com.nexusapp.api.repository.SeqContaRepository;
import br.com.nexusapp.api.service.ISeqContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SeqContaServiceImpl implements ISeqContaService {

	private static final int QTD_DIGITOS_CONTA = 3;

	private static final int QTD_DIGITOS_SEQ_CONTA = 7;
	
    private final SeqContaRepository repository;

	@Autowired
	public SeqContaServiceImpl(SeqContaRepository repository) {
		this.repository = repository;
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public synchronized String gerarNumeroConta(Conta conta) {
    	long ano = LocalDate.now().getYear();
		long sequencial = obterCodigoSequencialPorTipo(conta);
		String nrTipo = this.obterStringZerosPrefixado(conta.getId(), QTD_DIGITOS_CONTA);
		String nrSequencial = this.obterStringZerosPrefixado(sequencial, QTD_DIGITOS_SEQ_CONTA);
		return "NXCT" + ano + nrTipo + nrSequencial;
	}
    
    private long obterCodigoSequencialPorTipo(Conta conta) {

		long sequencial = 0L;
		long ano = LocalDate.now().getYear();

		SeqConta seqConta = buscaCodigoPorAnoTipo(conta, ano);

		if(Optional.ofNullable(seqConta).isEmpty() || Optional.ofNullable(seqConta.getId()).isEmpty()) {
			seqConta = new SeqConta();
			seqConta.setNrAno(ano);
			seqConta.setConta(conta);
		} else {
			sequencial = seqConta.getNrSequencial();
		}

		seqConta.setNrSequencial(++sequencial);

		repository.saveAndFlush(seqConta);

		return sequencial;
	}

	private SeqConta buscaCodigoPorAnoTipo(Conta conta, Long nrAno) {
		return repository.findByContaAndNrAno(conta, nrAno);
	}

	private String obterStringZerosPrefixado(final long valor, final int quantidade) {
		return "%0" + quantidade + "d" + valor;
	}
}