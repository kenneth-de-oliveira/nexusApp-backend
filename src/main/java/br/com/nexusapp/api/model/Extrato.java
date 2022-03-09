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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.nexusapp.api.dtos.InfoContaDTO;
import br.com.nexusapp.api.enums.OperacaoEnum;

@Entity
@Table(name = "tb_extrato")
@SequenceGenerator(name = "extrato_seq", sequenceName = "extrato_seq", initialValue = 1, allocationSize = 1)
public class Extrato implements Serializable {

	private static final long serialVersionUID = -7816515911697877804L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "extrato_seq")
	private Long id;

	@NotBlank
	@Column(name = "cl_numero", nullable = false)
	private String numero;

	@NotBlank
	@Column(name = "cl_agencia", nullable = false)
	private String agencia;

	@NotNull
	@Column(name = "cl_valor", nullable = false)
	private double valor;

	@NotNull
	@Enumerated(EnumType.STRING)
	private OperacaoEnum operacao;

	@NotNull
	@Column(name = "data_extrato", nullable = false)
	private LocalDateTime dataExtrato;

	public Extrato(InfoContaDTO infoContaDTO, OperacaoEnum operacaoEnum) {
		this.agencia = infoContaDTO.getAgencia();
		this.numero = infoContaDTO.getNumero();
		this.valor = infoContaDTO.getValor();
		this.operacao = operacaoEnum;
	}

	@PrePersist
	void persist() {
		dataExtrato = LocalDateTime.now();
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

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public OperacaoEnum getOperacao() {
		return operacao;
	}

	public void setOperacao(OperacaoEnum operacao) {
		this.operacao = operacao;
	}

	public LocalDateTime getDataExtrato() {
		return dataExtrato;
	}

	public void setDataExtrato(LocalDateTime dataExtrato) {
		this.dataExtrato = dataExtrato;
	}

}