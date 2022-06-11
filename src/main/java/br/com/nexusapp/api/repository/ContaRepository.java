package br.com.nexusapp.api.repository;

import br.com.nexusapp.api.enums.ContaStatus;
import br.com.nexusapp.api.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM tb_conta WHERE fk_cliente = :idCliente")
    Optional<Conta> consultaPorIdCliente(Long idCliente);
    Optional<Conta> findByIdAndStatus(Long id, ContaStatus status);
    Optional<Conta> findByAgencia(String agencia);
    Optional<Conta> findByAgenciaAndNumeroAndStatus(String agencia, String numero, ContaStatus status);
    Optional<Conta> findByNumero(String numero);
}
