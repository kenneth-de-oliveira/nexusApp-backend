package br.com.nexusapp.api.model;

import br.com.nexusapp.api.dtos.ClienteEnderecoDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cliente_endereco")
@SequenceGenerator(name = "cliente_endereco_seq", sequenceName = "cliente_endereco_seq", initialValue = 1, allocationSize = 1)
public class ClienteEndereco implements Serializable {

	private static final long serialVersionUID = 6637135061404803930L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_endereco_seq")
    private Long id;

    @Column(name = "ek_cliente", nullable = false)
    private Long ekCliente;

    @Column(name = "ek_endereco", nullable = false)
    private Long ekEndereco;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void persist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public ClienteEnderecoDTO toDTO() {
        ClienteEnderecoDTO clienteEnderecoDTO = new ClienteEnderecoDTO();
        clienteEnderecoDTO.setEkCliente(this.ekCliente);
        clienteEnderecoDTO.setEkEndereco(this.ekEndereco);
        clienteEnderecoDTO.setCreatedAt(this.createdAt);
        clienteEnderecoDTO.setUpdatedAt(this.updatedAt);
        return clienteEnderecoDTO;
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
