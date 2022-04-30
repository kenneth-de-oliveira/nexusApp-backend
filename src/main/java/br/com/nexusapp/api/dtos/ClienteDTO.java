package br.com.nexusapp.api.dtos;

import br.com.nexusapp.api.model.Cliente;

public class ClienteDTO extends ClienteFullDTO {

	private EnderecoDTO enderecoDTO;

	public ClienteDTO(Cliente cliente, EnderecoDTO enderecoDTO) {
		super(cliente);
		this.enderecoDTO = enderecoDTO;
	}

	public ClienteDTO() {
	}

	@Override
	public EnderecoDTO getEnderecoDTO() {
		return enderecoDTO;
	}

	@Override
	public void setEnderecoDTO(EnderecoDTO enderecoDTO) {
		this.enderecoDTO = enderecoDTO;
	}
}