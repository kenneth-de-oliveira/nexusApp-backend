package br.com.nexusapp.api.dtos;

import br.com.nexusapp.api.enums.BoletoStatus;
import br.com.nexusapp.api.model.Boleto;

import java.time.LocalDateTime;

public class BoletoDTO {
    private Long id;
    private String codigo;
    private double valor;
    private String cedente;
    private String emissor;
    private String status;
    private LocalDateTime dataVencimento;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Boleto toModel() {
        var boleto = new Boleto();
        boleto.setEmissor(emissor);
        boleto.setValor(valor);
        boleto.setCodigo(codigo);
        return boleto;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCedente() {
        return cedente;
    }

    public void setCedente(String cedente) {
        this.cedente = cedente;
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
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
