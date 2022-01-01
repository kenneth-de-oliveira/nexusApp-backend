package br.com.nexusapp.api.repository;

import br.com.nexusapp.api.model.ClienteEndereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteEnderecoRepository extends JpaRepository<ClienteEndereco, Long> {
}
