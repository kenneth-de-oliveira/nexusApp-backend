package br.com.nexusapp.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.nexusapp.api.dtos.ClienteDTO;
import br.com.nexusapp.api.dtos.EnderecoDTO;
import br.com.nexusapp.api.exception.ConflictException;
import br.com.nexusapp.api.exception.NotFoundException;
import br.com.nexusapp.api.model.Cliente;
import br.com.nexusapp.api.model.Usuario;
import br.com.nexusapp.api.repository.ClienteRepository;
import br.com.nexusapp.api.repository.UsuarioRepository;
import br.com.nexusapp.api.service.IClienteService;
import br.com.nexusapp.api.service.IEnderecoService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final IEnderecoService enderecoService;
    private final MessageSource ms;
    
    @Autowired
    public ClienteServiceImpl(ClienteRepository repository, UsuarioRepository usuarioRepository,
		IEnderecoService enderecoService, MessageSource ms) {
		this.repository = repository;
		this.usuarioRepository = usuarioRepository;
		this.enderecoService = enderecoService;
		this.ms = ms;
	}

	@Override
    public ClienteDTO cadastrar(ClienteDTO clienteDTO) {
        var clienteOpt = repository.findByDocumento(clienteDTO.getDocumento());
        if (clienteOpt.isPresent()) {
            throw new ConflictException(ms.getMessage("cliente.cadastro.erro",
        null, LocaleContextHolder.getLocale()));
        }

        var cliente = salvarCredenciaisDoCliente(clienteDTO);

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
        return getClienteDTO(clienteOpt.get());
    }

    @Override
    public ClienteDTO buscarClientePorId(Long idCliente) {
        Optional<Cliente> clienteOpt = repository.findById(idCliente);
        if (clienteOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("cliente.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
        return getClienteDTO(clienteOpt.get());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ClienteDTO update(Long idCliente, ClienteDTO clienteDto) {
        Optional<Cliente> clienteOpt = repository.findById(idCliente);
        if (clienteOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("cliente.consulta.erro",
                    null, LocaleContextHolder.getLocale()));
        }
        return atualizarCliente(clienteOpt.get(), clienteDto);
    }

    private ClienteDTO atualizarCliente(Cliente clienteOld, ClienteDTO clienteNewDto) {
        clienteOld.setEmail(clienteNewDto.getEmail());
        clienteOld.setTelefone(clienteNewDto.getTelefone());
        repository.save(clienteOld);
        return clienteOld.toDTO();
    }

    private ClienteDTO getClienteDTO(Cliente cliente) {
        EnderecoDTO enderecoDTO =buscarEnderecoCliente(cliente.toDTO());
        enderecoDTO.setIdCliente(cliente.getId());
        return new ClienteDTO(cliente, enderecoDTO);
    }

    private EnderecoDTO buscarEnderecoCliente(ClienteDTO clienteDTO) {
        return enderecoService.buscarDoClientePorId(clienteDTO.getId());
    }
    
    private Cliente salvarCredenciaisDoCliente(ClienteDTO clienteDTO) {
    	var cliente = clienteDTO.toModel();
        var usuario = new Usuario();
        
    	usuario.setUsername(clienteDTO.getUsuarioDTO().getUsername());
        usuario.setPassword(clienteDTO.getUsuarioDTO().getPassword());
        
        cliente.setIdUsuario(usuarioRepository.save(usuario).getId());
        
        repository.save(cliente);

        return cliente;
    }

	private String setEncodedPassword(ClienteDTO clienteDTO) {
		return new BCryptPasswordEncoder().encode(clienteDTO.getUsuarioDTO().getPassword());
	}
    
}
