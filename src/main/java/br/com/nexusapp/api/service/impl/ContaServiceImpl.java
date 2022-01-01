package br.com.nexusapp.api.service.impl;

import br.com.nexusapp.api.dtos.ClienteDTO;
import br.com.nexusapp.api.dtos.ContaDTO;
import br.com.nexusapp.api.dtos.ContaFullDTO;
import br.com.nexusapp.api.exception.ConflictException;
import br.com.nexusapp.api.exception.NotFoundException;
import br.com.nexusapp.api.model.Conta;
import br.com.nexusapp.api.repository.ContaRepository;
import br.com.nexusapp.api.service.IClienteService;
import br.com.nexusapp.api.service.IContaService;
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
    private final IClienteService clienteService;
    private final MessageSource ms;

    @Autowired
    public ContaServiceImpl(ContaRepository repository, IClienteService clienteService, MessageSource ms) {
        this.repository = repository;
        this.clienteService = clienteService;
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
        ClienteDTO clienteDTO = clienteService.buscarClientePorId(contaDTO.getIdCliente());
        contaDTO.setClienteDTO(clienteDTO);
        var conta = contaDTO.toModel();
        conta.setCliente(clienteDTO.toModel());
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

}
