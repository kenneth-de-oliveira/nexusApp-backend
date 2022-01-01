package br.com.nexusapp.api.repository;

import br.com.nexusapp.api.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    @Query(nativeQuery = true, value = "SELECT tb_endereco.* FROM tb_endereco " +
    "JOIN tb_cliente_endereco ON tb_endereco.id = tb_cliente_endereco.id " +
    "JOIN tb_cliente ON tb_cliente_endereco.ek_cliente = tb_cliente.id " +
    "WHERE tb_cliente.cl_documento = :cpf " +
    "ORDER BY tb_endereco.updated_at DESC")
    List<Endereco> findAllDoClientePorCpf(String cpf);
}
