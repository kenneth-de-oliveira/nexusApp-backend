package br.com.nexusapp.api.model;

import br.com.nexusapp.api.dtos.AgenciaDTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_agencia")
@SequenceGenerator(name = "agencia_seq", sequenceName = "agencia_seq", initialValue = 1, allocationSize = 1)
public class Agencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agencia_seq")
    private Long id;

    @Column(name = "cl_numero", nullable = false, unique = true)
    private String numero;

    public Agencia() {}

    public AgenciaDTO toDTO() {
        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setId(this.id);
        agenciaDTO.setNumero(this.numero);
        return agenciaDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agencia agencia = (Agencia) o;

        if (!id.equals(agencia.id)) return false;
        return numero.equals(agencia.numero);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + numero.hashCode();
        return result;
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