package br.com.nexusapp.api.service;

import br.com.nexusapp.api.dtos.ContaDTO;
import br.com.nexusapp.api.dtos.ContaFullDTO;

public interface IContaService {
    ContaFullDTO cadastrar(ContaDTO contaDTO);
    ContaFullDTO buscarContaPorId(Long id);
    ContaFullDTO buscarContaPorNumero(String numero);
    ContaFullDTO buscarContaAgencia(String agencia);
}
