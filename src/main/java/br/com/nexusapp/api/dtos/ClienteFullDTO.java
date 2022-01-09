package br.com.nexusapp.api.dtos;

import br.com.nexusapp.api.enums.ClienteStatus;
import br.com.nexusapp.api.model.Cliente;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class ClienteFullDTO {
    private Long id;
    @NotBlank private String nome;
    @NotBlank private String sobrenome;
    @CPF @NotBlank private String documento;
    @NotBlank private String email;
    @Pattern(regexp = "^[0-9]{2}([0-9]{8}|[0-9]{9})")
    @NotBlank private String telefone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ClienteStatus status;
    private EnderecoDTO enderecoDTO;

    public ClienteFullDTO() {
    }

    public ClienteFullDTO(Cliente cliente) {
        this.setCreatedAt(cliente.getCreatedAt());
        this.setUpdatedAt(cliente.getUpdatedAt());
        this.setDocumento(cliente.getDocumento());
        this.setTelefone(cliente.getTelefone());
        this.setEmail(cliente.getEmail());
        this.setId(cliente.getId());
        this.setSobrenome(cliente.getSobrenome());
        this.setStatus(cliente.getStatus());
        this.setNome(cliente.getNome());
    }

    public Cliente toModel() {
        Cliente cliente = new Cliente();
        cliente.setCreatedAt(this.createdAt);
        cliente.setUpdatedAt(this.updatedAt);
        cliente.setDocumento(this.documento);
        cliente.setTelefone(this.telefone);
        cliente.setEmail(this.email);
        cliente.setId(this.id);
        cliente.setSobrenome(this.sobrenome);
        cliente.setStatus(this.status);
        cliente.setNome(this.nome);
        return cliente;
    }

    public EnderecoDTO getEnderecoDTO() {
        return enderecoDTO;
    }

    public void setEnderecoDTO(EnderecoDTO enderecoDTO) {
        this.enderecoDTO = enderecoDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public ClienteStatus getStatus() {
        return status;
    }

    public void setStatus(ClienteStatus status) {
        this.status = status;
    }
}