package br.com.nexusapp.api.dtos;

import br.com.nexusapp.api.model.ClienteEndereco;

import java.time.LocalDateTime;

public class ClienteEnderecoDTO {

    private Long id;
    private Long ekCliente;
    private Long ekEndereco;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ClienteEnderecoDTO() {
    }

    public ClienteEnderecoDTO(Long ekCliente, Long ekEndereco) {
        this.ekCliente = ekCliente;
        this.ekEndereco = ekEndereco;
    }

    public ClienteEndereco toModel() {
        ClienteEndereco clienteEndereco = new ClienteEndereco();
        clienteEndereco.setEkCliente(this.ekCliente);
        clienteEndereco.setEkEndereco(this.ekEndereco);
        clienteEndereco.setCreatedAt(this.createdAt);
        clienteEndereco.setUpdatedAt(this.updatedAt);
        return clienteEndereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEkCliente() {
        return ekCliente;
    }

    public void setEkCliente(Long ekCliente) {
        this.ekCliente = ekCliente;
    }

    public Long getEkEndereco() {
        return ekEndereco;
    }

    public void setEkEndereco(Long ekEndereco) {
        this.ekEndereco = ekEndereco;
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
}
