package br.com.nexusapp.api.dtos;

import javax.validation.constraints.NotBlank;

public class InfoContaFullDTO extends InfoContaDTO {
    @NotBlank
    private String agenciaDestino;

    @NotBlank
    private String numeroDestino;

    public String getAgenciaDestino() {
        return agenciaDestino;
    }

    public void setAgenciaDestino(String agenciaDestino) {
        this.agenciaDestino = agenciaDestino;
    }

    public String getNumeroDestino() {
        return numeroDestino;
    }

    public void setNumeroDestino(String numeroDestino) {
        this.numeroDestino = numeroDestino;
    }
}