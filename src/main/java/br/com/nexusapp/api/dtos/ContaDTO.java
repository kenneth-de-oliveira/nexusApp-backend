package br.com.nexusapp.api.dtos;

import javax.validation.constraints.NotNull;

public class ContaDTO extends ContaFullDTO{

    @NotNull
    private Long idCliente;

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
}
