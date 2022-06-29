package br.com.nexusapp.api.service;

import br.com.nexusapp.api.dtos.EnderecoDTO;

import java.util.List;

public interface IEnderecoService {
    EnderecoDTO cadastrar(Long idCliente, EnderecoDTO enderecoDTO);
    List<EnderecoDTO> listaDoClientePorCpf(String cpf);
    EnderecoDTO buscarDoClientePorId(Long id);
    EnderecoDTO update(Long idCliente, EnderecoDTO enderecoDTO);
}