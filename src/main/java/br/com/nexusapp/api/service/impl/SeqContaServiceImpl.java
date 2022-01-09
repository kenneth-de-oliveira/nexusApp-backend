package br.com.nexusapp.api.service.impl;

import br.com.nexusapp.api.model.Cliente;
import br.com.nexusapp.api.model.SeqConta;
import br.com.nexusapp.api.repository.SeqContaRepository;
import br.com.nexusapp.api.service.ISeqContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public synchronized String gerarNumeroContaCliente(Cliente cliente) {

        long ano = LocalDate.now().getYear();
        long sequencial = obterCodigoSequencialPorTipo(cliente);

        String nrTipo = this.obterStringZerosPrefixado(cliente.getId(), QTD_DIGITOS_CONTA);
        String nrSequencial = this.obterStringZerosPrefixado(sequencial, QTD_DIGITOS_SEQ_CONTA);

        return ano + nrTipo + nrSequencial;
    }

    private long obterCodigoSequencialPorTipo(Cliente cliente) {

        long sequencial = 0L;
        long ano = LocalDate.now().getYear();

        SeqConta seqConta = buscaCodigoPorAnoTipo(cliente.getId(), ano);

        if (Optional.ofNullable(seqConta).isEmpty() || Optional.ofNullable(seqConta.getId()).isEmpty()) {
            seqConta = new SeqConta();
            seqConta.setNrAno(ano);
            seqConta.setIdCliente(cliente.getId());
        } else {
            sequencial = seqConta.getNrSequencial();
        }

        seqConta.setNrSequencial(++sequencial);

        repository.saveAndFlush(seqConta);

        return sequencial;
    }

    private SeqConta buscaCodigoPorAnoTipo(Long idConta, Long nrAno) {
        return repository.findByIdAndNrAno(idConta, nrAno);
    }

    private String obterStringZerosPrefixado(final long valor, final int quantidade) {
        return String.format("%0" + quantidade + "d", valor);
    }
}