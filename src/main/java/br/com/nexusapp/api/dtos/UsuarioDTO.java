package br.com.nexusapp.api.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.nexusapp.api.model.Usuario;

public class UsuarioDTO {
	private Long id;
	
	@Size(min = 3, max = 150)
	@NotEmpty(message = "{campo.username.obrigatorio}")
    private String username;
	
	@Size(min = 8, max = 150)
	@NotEmpty(message = "{campo.password.obrigatorio}")
    private String password;
    
	public Usuario toModel() {
		var usuario = new Usuario();
		usuario.setId(id);
		usuario.setUsername(username);
		usuario.setPassword(password);
		return usuario;
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
