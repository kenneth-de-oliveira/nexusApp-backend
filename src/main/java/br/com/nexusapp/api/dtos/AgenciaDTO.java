package br.com.nexusapp.api.dtos;

import br.com.nexusapp.api.model.Agencia;

public class AgenciaDTO {
    private Long id;
    private String numero;

    public Agencia toModel() {
        Agencia agencia = new Agencia();
        agencia.setId(this.id);
        agencia.setNumero(this.numero);
        return agencia;
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
}