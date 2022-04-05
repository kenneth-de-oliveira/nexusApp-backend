package br.com.nexusapp.api.repository;

import br.com.nexusapp.api.enums.BoletoStatus;
import br.com.nexusapp.api.model.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoletoRepository extends JpaRepository<Boleto, Long> {
    Optional<Boleto> findByCodigo(String codigo);
    Optional<Boleto> findByCodigoAndStatus(String codigo, BoletoStatus status);
}
