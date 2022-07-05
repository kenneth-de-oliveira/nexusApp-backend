package br.com.nexusapp.api.service;

import br.com.nexusapp.api.dtos.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IContaService extends UserDetailsService {
    ContaFullDTO consultarSaldo(String agencia, String numero);
	ContaFullDTO buscarContaPorCpf(String cpf);
    ContaFullDTO cadastrar(ContaDTO contaDTO);
    ContaFullDTO buscarContaPorId(Long id);
    ContaFullDTO buscarContaPorNumero(String numero);
    ContaFullDTO buscarContaAgencia(String agencia);
//    byte[] extratoPdfConta(Long id);
    void depositar(InfoContaDTO contaDto);
    void sacar(InfoContaDTO infoContaDTO);
    void transferir(InfoContaFullDTO infoContaFullDTO);
    void deletaContaById(Long id);
    List<ExtratoDTO> listarExtratos(Long idConta);
    ContaFullDTO buscarContaPorNomeUsuario(String nomeUsuario);
}
