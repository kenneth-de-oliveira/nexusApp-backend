package br.com.nexusapp.api.repository;

import br.com.nexusapp.api.model.Conta;
import br.com.nexusapp.api.model.SeqConta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeqContaRepository extends JpaRepository<SeqConta, Long> {
	SeqConta findByContaAndNrAno(Conta conta, Long nrAno);
}