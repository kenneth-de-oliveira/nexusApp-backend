package br.com.nexusapp.api.service.impl;

import br.com.nexusapp.api.model.Cliente;
import br.com.nexusapp.api.model.SeqAgencia;
import br.com.nexusapp.api.repository.SeqAgenciaRepository;
import br.com.nexusapp.api.service.ISeqAgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SeqAgenciaServiceImpl implements ISeqAgenciaService {

    private static final int QTD_DIGITOS_CONTA = 2;

    private static final int QTD_DIGITOS_SEQ_CONTA = 2;

    private final SeqAgenciaRepository repository;

    @Autowired
    public SeqAgenciaServiceImpl(SeqAgenciaRepository repository) {
        this.repository = repository;
    }

    @Override
    public synchronized String gerarNumeroAgenciaCliente(Cliente cliente) {

        long ano = LocalDate.now().getYear();
        long sequencial = obterCodigoSequencialPorTipo(cliente);

        String nrTipo = this.obterStringZerosPrefixado(cliente.getId(), QTD_DIGITOS_CONTA);
        String nrSequencial = this.obterStringZerosPrefixado(sequencial, QTD_DIGITOS_SEQ_CONTA);

        return ano + nrTipo + nrSequencial;
    }

    private long obterCodigoSequencialPorTipo(Cliente cliente) {

        long sequencial = 0L;
        long ano = LocalDate.now().getYear();

        SeqAgencia seqAgencia = buscaCodigoPorAnoTipo(cliente.getId(), ano);

        if (Optional.ofNullable(seqAgencia).isEmpty() || Optional.ofNullable(seqAgencia.getId()).isEmpty()) {
            seqAgencia = new SeqAgencia();
            seqAgencia.setNrAno(ano);
            seqAgencia.setIdConta(cliente.getId());
        } else {
            sequencial = seqAgencia.getNrSequencial();
        }

        seqAgencia.setNrSequencial(++sequencial);

        repository.saveAndFlush(seqAgencia);

        return sequencial;
    }

    private SeqAgencia buscaCodigoPorAnoTipo(Long idConta, Long nrAno) {
        return repository.findByIdAndNrAno(idConta, nrAno);
    }

    private String obterStringZerosPrefixado(final long valor, final int quantidade) {
        return String.format("%0" + quantidade + "d", valor);
    }
}