package br.com.nexusapp.api.repository;

import br.com.nexusapp.api.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM tb_conta WHERE fk_cliente = :idCliente")
    Optional<Conta> consultaPorIdCliente(Long idCliente);
    Optional<Conta> findByAgencia(String agencia);
    Optional<Conta> findByAgenciaAndNumero(String agencia, String numero);
    Optional<Conta> findByNumero(String numero);
}
