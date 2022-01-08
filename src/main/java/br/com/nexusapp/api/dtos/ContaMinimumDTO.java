package br.com.nexusapp.api.dtos;

public class ContaMinimumDTO extends ContaFullDTO{

    private EnderecoDTO enderecoDTO;

    public ContaMinimumDTO(ContaFullDTO contaFullDTO) {
        super(contaFullDTO);
    }

    public EnderecoDTO getEnderecoDTO() {
        return enderecoDTO;
    }

    public void setEnderecoDTO(EnderecoDTO enderecoDTO) {
        this.enderecoDTO = enderecoDTO;
    }
}