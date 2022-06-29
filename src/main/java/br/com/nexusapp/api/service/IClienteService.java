package br.com.nexusapp.api.service;

import br.com.nexusapp.api.dtos.ClienteDTO;

public interface IClienteService {
    ClienteDTO cadastrar(ClienteDTO clienteDTO);
    ClienteDTO buscarClientePorCpf(String cpf);
    ClienteDTO buscarClientePorId(Long idCliente);
    ClienteDTO update(Long idCliente, ClienteDTO clienteDto);
}