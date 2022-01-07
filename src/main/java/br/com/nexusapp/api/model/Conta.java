package br.com.nexusapp.api.model;

import br.com.nexusapp.api.dtos.ContaFullDTO;
import br.com.nexusapp.api.enums.ContaStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_conta")
@SequenceGenerator(name = "conta_seq", sequenceName = "conta_seq", initialValue = 1, allocationSize = 1)
public class Conta implements Serializable {

    private static final long serialVersionUID = 179605372349941397L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conta_seq")
    private Long id;

    @Column(name = "cl_numero", nullable = false, unique = true)
    private String numero;

    @OneToOne
    @JoinColumn(name = "fk_agencia", nullable = false)
    private Agencia agencia;

    @Column(name = "cl_saldo", nullable = false)
    private double saldo;

    @Column(name = "cl_limite", nullable = false)
    private double limite;

    @Enumerated(EnumType.STRING)
    private ContaStatus status;

    @ManyToOne
    @JoinColumn(name = "fk_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void persist() {
        status = ContaStatus.of(1);
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        this.saldo = 0;
        this.limite = 0;
    }

    public ContaFullDTO toDTO() {
        ContaFullDTO contaFullDTO = new ContaFullDTO();
        contaFullDTO.setAgenciaDTO(this.agencia.toDTO());
        contaFullDTO.setCreatedAt(this.createdAt);
        contaFullDTO.setClienteDTO(this.cliente.toDTO());
        contaFullDTO.setUpdatedAt(this.updatedAt);
        contaFullDTO.setLimite(this.limite);
        contaFullDTO.setId(this.id);
        contaFullDTO.setSaldo(this.saldo);
        contaFullDTO.setNumero(this.numero);
        contaFullDTO.setStatus(this.status);
        return contaFullDTO;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public ContaStatus getStatus() {
        return status;
    }

    public void setStatus(ContaStatus status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    @Override
    public String toString() {
        return "Conta{" + "id=" + id +
                ", numero='" + numero + '\'' +
                ", agencia='" + agencia + '\'' +
                ", saldo=" + saldo +
                ", limite=" + limite +
                ", status=" + status +
                ", cliente=" + cliente +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conta)) return false;

        Conta conta = (Conta) o;

        return getId().equals(conta.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
