package br.com.nexusapp.api.service;

import br.com.nexusapp.api.dtos.ContaDTO;
import br.com.nexusapp.api.dtos.ContaFullDTO;
import br.com.nexusapp.api.dtos.InfoContaDTO;
import br.com.nexusapp.api.dtos.InfoContaFullDTO;

public interface IContaService {
	ContaFullDTO buscarContaPorCpf(String cpf);
    ContaFullDTO cadastrar(ContaDTO contaDTO);
    ContaFullDTO buscarContaPorId(Long id);
    ContaFullDTO buscarContaPorNumero(String numero);
    ContaFullDTO buscarContaAgencia(String agencia);
    void depositar(InfoContaDTO contaDto);
    void sacar(InfoContaDTO infoContaDTO);
    void transferir(InfoContaFullDTO infoContaFullDTO);
}
