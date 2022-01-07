package br.com.nexusapp.api.service.impl;

import br.com.nexusapp.api.model.Agencia;
import br.com.nexusapp.api.model.SeqAgencia;
import br.com.nexusapp.api.repository.SeqAgenciaRepository;
import br.com.nexusapp.api.service.ISeqAgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ISeqAgenciaServiceImpl implements ISeqAgenciaService {

    private static final int QTD_DIGITOS_CONTA = 3;

    private static final int QTD_DIGITOS_SEQ_CONTA = 7;

    private final SeqAgenciaRepository repository;

    @Autowired
    public ISeqAgenciaServiceImpl(SeqAgenciaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized String gerarNumeroAgencia(Agencia agencia) {
        long ano = LocalDate.now().getYear();
        long sequencial = obterCodigoSequencialPorTipo(agencia);
        String nrTipo = this.obterStringZerosPrefixado(agencia.getId(), QTD_DIGITOS_CONTA);
        String nrSequencial = this.obterStringZerosPrefixado(sequencial, QTD_DIGITOS_SEQ_CONTA);
        return "NXAG" + ano + nrTipo + nrSequencial;
    }

    private long obterCodigoSequencialPorTipo(Agencia agencia) {

        long sequencial = 0L;
        long ano = LocalDate.now().getYear();

        SeqAgencia seqAgencia = buscaCodigoPorAnoTipo(agencia, ano);

        if(Optional.ofNullable(seqAgencia).isEmpty() || Optional.ofNullable(seqAgencia.getId()).isEmpty()) {
            seqAgencia = new SeqAgencia();
            seqAgencia.setNrAno(ano);
            seqAgencia.setAgencia(agencia);
        } else {
            sequencial = seqAgencia.getNrSequencial();
        }

        seqAgencia.setNrSequencial(++sequencial);

        repository.saveAndFlush(seqAgencia);

        return sequencial;
    }

    private SeqAgencia buscaCodigoPorAnoTipo(Agencia agencia, Long nrAno) {
        return repository.findByAgenciaAndNrAno(agencia, nrAno);
    }

    private String obterStringZerosPrefixado(final long valor, final int quantidade) {
        return "%0" + quantidade + "d" + valor;
    }
}