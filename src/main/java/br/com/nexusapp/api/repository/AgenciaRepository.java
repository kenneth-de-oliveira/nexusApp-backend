package br.com.nexusapp.api.repository;

import br.com.nexusapp.api.model.Agencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenciaRepository extends JpaRepository<Agencia, Long> {
}