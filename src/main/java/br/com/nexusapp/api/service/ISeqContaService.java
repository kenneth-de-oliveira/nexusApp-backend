package br.com.nexusapp.api.service;

import br.com.nexusapp.api.model.Conta;

public interface ISeqContaService {
	String gerarNumeroConta(Conta conta);
}