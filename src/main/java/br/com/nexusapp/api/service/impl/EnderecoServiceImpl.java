package br.com.nexusapp.api.service.impl;

import br.com.nexusapp.api.dtos.ClienteEnderecoDTO;
import br.com.nexusapp.api.dtos.EnderecoDTO;
import br.com.nexusapp.api.exception.NotFoundException;
import br.com.nexusapp.api.model.Endereco;
import br.com.nexusapp.api.repository.EnderecoRepository;
import br.com.nexusapp.api.service.IClienteEnderecoService;
import br.com.nexusapp.api.service.IEnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoServiceImpl implements IEnderecoService {

    private final IClienteEnderecoService clienteEnderecoService;
    private final EnderecoRepository repository;
    private final MessageSource ms;

    @Autowired
    public EnderecoServiceImpl(IClienteEnderecoService clienteEnderecoService, EnderecoRepository repository, MessageSource ms) {
        this.clienteEnderecoService = clienteEnderecoService;
        this.repository = repository;
        this.ms = ms;
    }

    @Override
    public EnderecoDTO cadastrar(Long idCliente, EnderecoDTO enderecoDTO) {
        Endereco endereco = repository.save(enderecoDTO.toModel());
        ClienteEnderecoDTO clienteEnderecoDTO = new ClienteEnderecoDTO(idCliente, endereco.getId());
        clienteEnderecoService.cadastrar(clienteEnderecoDTO);
        return endereco.toDTO();
    }

    @Override
    public List<EnderecoDTO> listaDoClientePorCpf(String cpf) {
        return repository.findAllDoClientePorCpf(cpf).stream()
            .map(Endereco::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public EnderecoDTO buscarDoClientePorId(Long id) {
        Optional<Endereco> enderecoOpt = repository.findById(id);
        if (enderecoOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("endereco.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
        return enderecoOpt.get().toDTO();
    }
}
