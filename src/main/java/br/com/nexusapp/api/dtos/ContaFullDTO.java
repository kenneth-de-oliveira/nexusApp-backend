package br.com.nexusapp.api.dtos;

import br.com.nexusapp.api.enums.ContaStatus;
import br.com.nexusapp.api.model.Conta;

import java.time.LocalDateTime;

public class ContaFullDTO {
    private Long id;
    private String numero;
    private AgenciaDTO agenciaDTO;
    private double saldo;
    private double limite;
    private ContaStatus status;
    private ClienteDTO clienteDTO;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Conta toModel() {
        Conta conta = new Conta();
        conta.setAgencia(this.agenciaDTO.toModel());
        conta.setCliente(this.clienteDTO.toModel());
        conta.setCreatedAt(this.createdAt);
        conta.setUpdatedAt(this.updatedAt);
        conta.setLimite(this.limite);
        conta.setId(this.id);
        conta.setSaldo(this.saldo);
        conta.setNumero(this.numero);
        conta.setStatus(this.status);
        return conta;
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

    public AgenciaDTO getAgenciaDTO() {
        return agenciaDTO;
    }

    public void setAgenciaDTO(AgenciaDTO agenciaDTO) {
        this.agenciaDTO = agenciaDTO;
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

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
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
