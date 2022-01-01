package br.com.nexusapp.api.model;

import br.com.nexusapp.api.dtos.EnderecoDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_endereco")
@SequenceGenerator(name = "endereco_seq", sequenceName = "endereco_seq", initialValue = 1, allocationSize = 1)
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_seq")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "cl_logradouro", length = 150, nullable = false)
    private String logradouro;

    @NotNull
    @Size(max = 50)
    @Column(name = "cl_numero", length = 50, nullable = false)
    private String numero;

    @NotNull
    @Size(max = 150)
    @Column(name = "cl_bairro", length = 150, nullable = false)
    private String bairro;

    @NotNull
    @Size(max = 150)
    @Column(name = "cl_cidade", length = 150, nullable = false)
    private String cidade;

    @NotNull
    @Size(max = 50)
    @Column(name = "cl_cep", length = 50, nullable = false)
    private String cep;

    @NotNull
    @Size(max = 2)
    @Column(name = "cl_uf", length = 2, nullable = false)
    private String uf;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void persist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public EnderecoDTO toDTO() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setBairro(this.bairro);
        enderecoDTO.setCep(this.cep);
        enderecoDTO.setCidade(this.cidade);
        enderecoDTO.setLogradouro(this.logradouro);
        enderecoDTO.setNumero(this.numero);
        enderecoDTO.setUf(this.uf);
        enderecoDTO.setCreatedAt(this.createdAt);
        enderecoDTO.setUpdatedAt(this.updatedAt);
        return enderecoDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco)) return false;

        Endereco endereco = (Endereco) o;

        return getId().equals(endereco.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
