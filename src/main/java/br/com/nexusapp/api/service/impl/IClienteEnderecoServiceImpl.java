package br.com.nexusapp.api.service.impl;

import br.com.nexusapp.api.dtos.ClienteEnderecoDTO;
import br.com.nexusapp.api.model.ClienteEndereco;
import br.com.nexusapp.api.repository.ClienteEnderecoRepository;
import br.com.nexusapp.api.service.IClienteEnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IClienteEnderecoServiceImpl implements IClienteEnderecoService {

    private final ClienteEnderecoRepository repository;
    private final MessageSource ms;

    @Autowired
    public IClienteEnderecoServiceImpl(ClienteEnderecoRepository repository, MessageSource ms) {
        this.repository = repository;
        this.ms = ms;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ClienteEnderecoDTO cadastrar(ClienteEnderecoDTO clienteEnderecoDTO) {
        ClienteEndereco clienteEndereco = repository.save(clienteEnderecoDTO.toModel());
        return clienteEndereco.toDTO();
    }
}
