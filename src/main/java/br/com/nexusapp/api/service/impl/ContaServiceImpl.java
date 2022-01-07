package br.com.nexusapp.api.service.impl;

import br.com.nexusapp.api.dtos.AgenciaDTO;
import br.com.nexusapp.api.dtos.ClienteDTO;
import br.com.nexusapp.api.dtos.ContaDTO;
import br.com.nexusapp.api.dtos.ContaFullDTO;
import br.com.nexusapp.api.exception.ConflictException;
import br.com.nexusapp.api.exception.NotFoundException;
import br.com.nexusapp.api.model.Conta;
import br.com.nexusapp.api.repository.ContaRepository;
import br.com.nexusapp.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ContaServiceImpl implements IContaService {

    private final ContaRepository repository;
    private final ISeqContaService iSeqContaService;
    private final ISeqAgenciaService iSeqAgenciaService;
    private final IClienteService clienteService;
    private final IAgenciaService iAgenciaService;
    private final MessageSource ms;

    @Autowired
    public ContaServiceImpl(ContaRepository repository, ISeqContaService iSeqContaService,
        ISeqAgenciaService iSeqAgenciaService, IClienteService clienteService,
        IAgenciaService iAgenciaService, MessageSource ms) {
        this.repository = repository;
        this.iSeqContaService = iSeqContaService;
        this.iSeqAgenciaService = iSeqAgenciaService;
        this.clienteService = clienteService;
        this.iAgenciaService = iAgenciaService;
        this.ms = ms;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ContaFullDTO cadastrar(ContaDTO contaDTO) {
        Optional<Conta> contaOpt = repository.consultaPorIdCliente(contaDTO.getIdCliente());
        if (contaOpt.isPresent()) {
            throw new ConflictException(ms.getMessage("conta.cadastro.erro",
        null, LocaleContextHolder.getLocale()));
        }
        contaDTO.setAgenciaDTO(iAgenciaService.buscarAgenciaPorId(contaDTO.getAgenciaDTO().getId()));
        ClienteDTO clienteDTO = clienteService.buscarClientePorId(contaDTO.getIdCliente());
        contaDTO.setClienteDTO(clienteDTO);
        var conta = contaDTO.toModel();
        conta.setCliente(clienteDTO.toModel());
        this.gerarAgenciaNumeroConta(contaDTO, conta);
        repository.save(conta);
        return conta.toDTO();
    }

    @Override
    public ContaFullDTO buscarContaPorId(Long id) {
        Optional<Conta> contaOpt = repository.findById(id);
        if (contaOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("conta.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
        return contaOpt.get().toDTO();
    }

    @Override
    public ContaFullDTO buscarContaAgencia(String agencia) {
        Optional<Conta> contaOpt = repository.findByAgencia(agencia);
        if (contaOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("conta.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
        return contaOpt.get().toDTO();
    }

    @Override
    public ContaFullDTO buscarContaPorNumero(String numero) {
        Optional<Conta> contaOpt = repository.findByNumero(numero);
        if (contaOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("conta.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
        return contaOpt.get().toDTO();
    }

    private void gerarAgenciaNumeroConta(ContaDTO contaDTO, Conta conta) {
        conta.getAgencia().setNumero(iSeqAgenciaService.gerarNumeroAgencia(contaDTO.getAgenciaDTO().toModel()));
        conta.setNumero(iSeqContaService.gerarNumeroConta(contaDTO.toModel()));
    }
}
