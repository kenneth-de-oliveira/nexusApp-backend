package br.com.nexusapp.api.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class InfoContaDTO {
    @NotBlank
    private String agencia;

    @NotBlank
    private String numero;

    @NotNull
    private double valor;

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
