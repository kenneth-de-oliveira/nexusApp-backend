package br.com.nexusapp.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.nexusapp.api.dtos.ClienteDTO;
import br.com.nexusapp.api.dtos.EnderecoDTO;
import br.com.nexusapp.api.enums.ClienteStatus;

@Entity
@Table(name = "tb_cliente")
@SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_seq", initialValue = 1, allocationSize = 1)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 6954008931923561600L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long idUsuario;

    @NotNull
    @Size(min = 3, max = 150)
    @Column(name = "cl_nome", length = 150, nullable = false)
    private String nome;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(name = "cl_documento", length = 11, nullable = false, unique = true)
    private String documento;

    @NotNull
    @Size(min = 3, max = 150)
    @Column(name = "cl_sobrenome", length = 150, nullable = false)
    private String sobrenome;

    @NotNull
    @Size(max = 150)
    @Column(name = "cl_email", length = 150, nullable = false)
    private String email;

    @NotNull
    @Column(name = "cl_telefone", length = 10, nullable = false)
    private String telefone;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ClienteStatus status;

    @PrePersist
    void persist() {
        status = ClienteStatus.of(1);
        createdAt = LocalDateTime.now();
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
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

  	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public ClienteDTO toDTO() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCreatedAt(this.createdAt);
        clienteDTO.setUpdatedAt(this.updatedAt);
        clienteDTO.setDocumento(this.documento);
        clienteDTO.setEmail(this.email);
        clienteDTO.setId(this.id);
        clienteDTO.setTelefone(this.telefone);
        clienteDTO.setSobrenome(this.sobrenome);
        clienteDTO.setStatus(this.status);
        clienteDTO.setNome(this.nome);
        return clienteDTO;
    }

    public ClienteDTO toFullDTO(EnderecoDTO enderecoDTO) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCreatedAt(this.createdAt);
        clienteDTO.setUpdatedAt(this.updatedAt);
        clienteDTO.setDocumento(this.documento);
        clienteDTO.setEmail(this.email);
        clienteDTO.setId(this.id);
        clienteDTO.setTelefone(this.telefone);
        clienteDTO.setSobrenome(this.sobrenome);
        clienteDTO.setStatus(this.status);
        clienteDTO.setNome(this.nome);
        clienteDTO.setEnderecoDTO(enderecoDTO);
        return clienteDTO;
    }
    
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", idUsuario=" + idUsuario + ", nome=" + nome + ", documento=" + documento
				+ ", sobrenome=" + sobrenome + ", email=" + email + ", telefone=" + telefone + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", status=" + status + "]";
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;

        Cliente cliente = (Cliente) o;

        return getId().equals(cliente.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
