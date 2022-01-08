package br.com.nexusapp.api.service;

import br.com.nexusapp.api.model.Cliente;

public interface ISeqContaService {
    String gerarNumeroContaCliente(Cliente cliente);
}