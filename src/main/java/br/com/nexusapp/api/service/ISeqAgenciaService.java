package br.com.nexusapp.api.service;

import br.com.nexusapp.api.model.Cliente;

public interface ISeqAgenciaService {
    String gerarNumeroAgenciaCliente(Cliente cliente);
}