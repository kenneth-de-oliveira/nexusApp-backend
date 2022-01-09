package br.com.nexusapp.api.dtos;

public class ContaDTO extends ContaFullDTO {

    @Override
    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    @Override
    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    private ClienteDTO clienteDTO;

    public ContaDTO() {
    }

    public ContaDTO(ContaFullDTO contaFullDTO) {
        super(contaFullDTO);
    }
}
