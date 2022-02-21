package br.com.nexusapp.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nexusapp.api.dtos.ClienteEnderecoDTO;
import br.com.nexusapp.api.model.ClienteEndereco;
import br.com.nexusapp.api.repository.ClienteEnderecoRepository;
import br.com.nexusapp.api.service.IClienteEnderecoService;

@Service
public class IClienteEnderecoServiceImpl implements IClienteEnderecoService {

    private final ClienteEnderecoRepository repository;
    

    @Autowired
    public IClienteEnderecoServiceImpl(ClienteEnderecoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClienteEnderecoDTO cadastrar(ClienteEnderecoDTO clienteEnderecoDTO) {
        ClienteEndereco clienteEndereco = repository.save(clienteEnderecoDTO.toModel());
        return clienteEndereco.toDTO();
    }
}
