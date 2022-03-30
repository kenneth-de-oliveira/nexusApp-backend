package br.com.nexusapp.api.dtos;

public class ExtratoDTO {

    private Long id;
    private String numero;
    private String agencia;
    private String valor;
    private String operacao;
    private String dataExtrato;

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

    public String getAgencia() {
        return agencia;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getDataExtrato() {
        return dataExtrato;
    }

    public void setDataExtrato(String dataExtrato) {
        this.dataExtrato = dataExtrato;
    }
}