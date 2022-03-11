package br.com.nexusapp.api.model;

import br.com.nexusapp.api.dtos.ExtratoDTO;
import br.com.nexusapp.api.dtos.InfoContaDTO;
import br.com.nexusapp.api.enums.OperacaoEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_extrato")
@SequenceGenerator(name = "extrato_seq", sequenceName = "extrato_seq", initialValue = 1, allocationSize = 1)
public class Extrato implements Serializable {

	private static final long serialVersionUID = -7816515911697877804L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "extrato_seq")
	private Long id;

	@Column(name = "cl_numero", nullable = false)
	private String numero;

	@Column(name = "cl_agencia", nullable = false)
	private String agencia;

	@Column(name = "cl_valor", nullable = false)
	private double valor;

	@Enumerated(EnumType.STRING)
	private OperacaoEnum operacao;

	@Column(name = "data_extrato", nullable = false)
	private LocalDateTime dataExtrato;

	public Extrato() {}

	public Extrato(InfoContaDTO infoContaDTO, OperacaoEnum operacaoEnum) {
		this.agencia = infoContaDTO.getAgencia();
		this.numero = infoContaDTO.getNumero();
		this.valor = infoContaDTO.getValor();
		this.operacao = operacaoEnum;
	}

	public ExtratoDTO toDTO() {
		ExtratoDTO extratoDTO = new ExtratoDTO();
		extratoDTO.setId(this.id);
		extratoDTO.setDataExtrato(this.dataExtrato);
		extratoDTO.setAgencia(this.agencia);
		extratoDTO.setNumero(this.numero);
		extratoDTO.setOperacao(this.operacao);
		return extratoDTO;
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