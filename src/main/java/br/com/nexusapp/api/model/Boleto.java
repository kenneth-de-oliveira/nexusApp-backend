package br.com.nexusapp.api.model;

import br.com.nexusapp.api.dtos.BoletoDTO;
import br.com.nexusapp.api.enums.BoletoStatus;
import br.com.nexusapp.api.enums.ParametroEnum;

import javax.persistence.*;

import static br.com.nexusapp.api.enums.ParametroEnum.NOME;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_boleto")
@SequenceGenerator(name = "boleto_seq", sequenceName = "boleto_seq", initialValue = 1, allocationSize = 1)
public class Boleto implements Serializable {

	private static final long serialVersionUID = -2365538255120794026L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boleto_seq")
    private Long id;

    @Column(name = "cl_codigo", length = 45, nullable = false, unique = true)
    private String codigo;

    @Column(name = "cl_valor", nullable = false)
    private double valor;

    @Column(name = "cl_cedente", nullable = false)
    private String cedente;

    @Enumerated(EnumType.STRING)
    private BoletoStatus status;

    @Column(name = "cl_emissor", nullable = false)
    private String emissor;

    @Column(name = "cl_data_vencimento", nullable = false)
    private LocalDateTime dataVencimento;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Boleto() {}

    public Boleto(String codigo, String emissor, LocalDateTime dataVencimento, LocalDateTime createdAt, BoletoStatus status) {
        this.codigo = codigo;
        this.emissor = emissor;
        this.dataVencimento = dataVencimento;
        this.createdAt = createdAt;
        this.status = status;
    }

    public BoletoDTO toDTO() {
        var boletoDTO = new BoletoDTO();
        boletoDTO.setId(id);
        boletoDTO.setCedente(cedente);
        boletoDTO.setEmissor(emissor);
        boletoDTO.setValor(valor);
        boletoDTO.setCodigo(codigo);
        boletoDTO.setDataVencimento(dataVencimento);
        boletoDTO.setStatus(this.status.getDescricao());
        boletoDTO.setCreatedAt(createdAt);
        boletoDTO.setUpdatedAt(updatedAt);
        return boletoDTO;
    }

	@PrePersist
	void persist() {
		cedente = ParametroEnum.of(NOME);
		dataVencimento = LocalDateTime.now().plusDays(3);
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
		status = BoletoStatus.ANALISE;
	}

    public Long getId() {
        return id;
    }

    public BoletoStatus getStatus() {
        return status;
    }

    public void setStatus(BoletoStatus status) {
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