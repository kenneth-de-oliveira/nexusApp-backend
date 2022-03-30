package br.com.nexusapp.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexusapp.api.model.Boleto;

public interface BoletoRepository extends JpaRepository<Boleto, Long> {
	Optional<Boleto> findByCodigo(String codigo);
}
