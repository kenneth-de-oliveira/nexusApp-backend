package br.com.nexusapp.api.dtos;

import br.com.nexusapp.api.model.Endereco;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


public class EnderecoDTO {
    private Long idCliente;
    @Size(min = 3, max = 150) @NotBlank private String logradouro;
    @Size(min = 3, max = 150) @NotBlank private String bairro;
    @NotBlank @Size(min = 1, max = 30) private String numero;
    @NotBlank @Size(min = 1, max = 150) @NotBlank private String cidade;
    @Size(min = 5, max = 30) @NotBlank private String cep;
    @Size(min = 2, max = 2) @NotBlank private String uf;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Endereco toModel() {
        Endereco endereco = new Endereco();
        endereco.setBairro(this.bairro);
        endereco.setCep(this.cep);
        endereco.setCidade(this.cidade);
        endereco.setLogradouro(this.logradouro);
        endereco.setNumero(this.numero);
        endereco.setUf(this.uf);
        endereco.setCreatedAt(this.createdAt);
        endereco.setUpdatedAt(this.updatedAt);
        return endereco;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
