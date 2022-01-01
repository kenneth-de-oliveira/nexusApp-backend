package br.com.nexusapp.api.service.impl;

import br.com.nexusapp.api.dtos.ClienteDTO;
import br.com.nexusapp.api.dtos.EnderecoDTO;
import br.com.nexusapp.api.exception.ConflictException;
import br.com.nexusapp.api.exception.NotFoundException;
import br.com.nexusapp.api.model.Cliente;
import br.com.nexusapp.api.repository.ClienteRepository;
import br.com.nexusapp.api.service.IClienteService;
import br.com.nexusapp.api.service.IEnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository repository;
    private final IEnderecoService enderecoService;
    private final MessageSource ms;

    @Autowired
    public ClienteServiceImpl(ClienteRepository repository, IEnderecoService enderecoService, MessageSource ms) {
        this.repository = repository;
        this.enderecoService = enderecoService;
        this.ms = ms;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ClienteDTO cadastrar(ClienteDTO clienteDTO) {
        Optional<Cliente> clienteOpt = repository.findByDocumento(clienteDTO.getDocumento());
        if (clienteOpt.isPresent()) {
            throw new ConflictException(ms.getMessage("cliente.cadastro.erro",
        null, LocaleContextHolder.getLocale()));
        }
        var cliente = clienteDTO.toModel();
        repository.save(cliente);
        return cliente.toFullDTO(enderecoService
        .cadastrar(cliente.getId(),
        clienteDTO.getEnderecoDTO()));
    }

    @Override
    public ClienteDTO buscarClientePorCpf(String cpf) {
        Optional<Cliente> clienteOpt = repository.findByDocumento(cpf);
        if (clienteOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("cliente.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
        return clienteOpt.get().toDTO();
    }

    @Override
    public ClienteDTO buscarClientePorId(Long idCliente) {
        Optional<Cliente> clienteOpt = repository.findById(idCliente);
        if (clienteOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("cliente.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
        return clienteOpt.get().toDTO();
    }
}
