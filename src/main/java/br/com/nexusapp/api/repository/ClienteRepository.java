package br.com.nexusapp.api.repository;

import java.util.Optional;

import br.com.nexusapp.api.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexusapp.api.model.Cliente;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByDocumento(String documento);
    @Query(nativeQuery = true, value = "SELECT * FROM tb_cliente WHERE usuario_id = :idUsuario")
    Optional<Cliente> consultaPorIdUsuario(Long idUsuario);
}
