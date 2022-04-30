package br.com.nexusapp.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.nexusapp.api.dtos.UsuarioDTO;

@Entity
@Table(name = "tb_usuario")
@SequenceGenerator(name = "sq_usuario", sequenceName = "sq_usuario", allocationSize = 1)
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3212579046174302219L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	public Usuario() {}

	public Usuario(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UsuarioDTO toDTO() {
		var usuarioDTO = new UsuarioDTO();
		usuarioDTO.setId(id);
		usuarioDTO.setUsername(username);
		usuarioDTO.setPassword(password);
		return usuarioDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}