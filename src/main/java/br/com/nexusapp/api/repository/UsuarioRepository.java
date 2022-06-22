package br.com.nexusapp.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexusapp.api.model.Usuario;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByUsername(String username);

	@Query(value = " SELECT US.* FROM TB_CONTA CO JOIN TB_CLIENTE CL ON CO.FK_CLIENTE = CL.ID " +
	" JOIN TB_USUARIO US ON CL.USUARIO_ID = US.ID WHERE CO.ID = ?1 ", nativeQuery = true)
	Optional<Usuario> buscarPorIdConta(Long idConta);
}
