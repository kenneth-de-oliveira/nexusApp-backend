package br.com.nexusapp.api.repository;

import br.com.nexusapp.api.model.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtratoRepository extends JpaRepository<Extrato, Long> {
    List<Extrato> findByAgenciaAndNumeroOrderByDataExtratoDesc(String agencia, String numero);
}