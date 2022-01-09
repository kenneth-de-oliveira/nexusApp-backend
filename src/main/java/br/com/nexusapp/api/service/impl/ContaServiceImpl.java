package br.com.nexusapp.api.service.impl;

import br.com.nexusapp.api.dtos.ClienteDTO;
import br.com.nexusapp.api.dtos.ContaDTO;
import br.com.nexusapp.api.dtos.ContaFullDTO;
import br.com.nexusapp.api.dtos.ContaMinimumDTO;
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
    private final IEnderecoService iEnderecoService;
    private final IClienteService clienteService;
    private final MessageSource ms;

    @Autowired
    public ContaServiceImpl(
        ContaRepository repository,
        ISeqContaService iSeqContaService,
        ISeqAgenciaService iSeqAgenciaService,
        IEnderecoService iEnderecoService,
        IClienteService clienteService,
        MessageSource ms) {
        this.repository = repository;
        this.iSeqContaService = iSeqContaService;
        this.iSeqAgenciaService = iSeqAgenciaService;
        this.iEnderecoService = iEnderecoService;
        this.clienteService = clienteService;
        this.ms = ms;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ContaFullDTO cadastrar(ContaDTO contaDTO) {

        ClienteDTO clienteDTO = cadastraClienteConta(contaDTO);

        var conta = contaDTO.toModel();
        conta.setNumero(iSeqContaService.gerarNumeroContaCliente(clienteDTO.toModel()));
        conta.setAgencia(iSeqAgenciaService.gerarNumeroAgenciaCliente(clienteDTO.toModel()));
        conta.setCliente(clienteDTO.toModel());
        repository.save(conta);

        return toMinimumDTO(clienteDTO, conta);
    }

    @Override
    public ContaFullDTO buscarContaPorId(Long id) {
        Optional<Conta> contaOpt = repository.findById(id);
        if (contaOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("conta.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
        return getContaMinimumDTO(contaOpt.get());
    }

    @Override
    public ContaFullDTO buscarContaAgencia(String agencia) {
        Optional<Conta> contaOpt = repository.findByAgencia(agencia);
        if (contaOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("conta.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
        return getContaMinimumDTO(contaOpt.get());
    }

    @Override
    public ContaFullDTO buscarContaPorNumero(String numero) {
        Optional<Conta> contaOpt = repository.findByNumero(numero);
        if (contaOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("conta.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
        return getContaMinimumDTO(contaOpt.get());
    }

    private ContaMinimumDTO toMinimumDTO(ClienteDTO clienteDTO, Conta conta) {
        ContaMinimumDTO contaMinimumDTO = new ContaMinimumDTO(conta.toDTO());
        contaMinimumDTO.setEnderecoDTO(clienteDTO.getEnderecoDTO());
        return contaMinimumDTO;
    }

    private ClienteDTO getCliente(ContaFullDTO contaDTO) {
        ClienteDTO clienteDTO = clienteService.buscarClientePorId(contaDTO.getClienteDTO().getId());
        clienteDTO.setEnderecoDTO(iEnderecoService.buscarDoClientePorId(clienteDTO.getId()));
        clienteDTO.getEnderecoDTO().setIdCliente(clienteDTO.getId());
        contaDTO.setClienteDTO(clienteDTO);
        return clienteDTO;
    }

    private ClienteDTO cadastraClienteConta(ContaDTO contaDTO) {
        contaDTO.setClienteDTO(clienteService.cadastrar(contaDTO.getClienteDTO()));
        ClienteDTO clienteDTO = getCliente(contaDTO);
        contaDTO.setClienteDTO(clienteDTO);
        return clienteDTO;
    }

    private ContaMinimumDTO getContaMinimumDTO(Conta conta) {
        ClienteDTO clienteDTO = getCliente(conta.toDTO());
        return toMinimumDTO(clienteDTO, conta);
    }
}
