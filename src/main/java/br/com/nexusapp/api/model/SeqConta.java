package br.com.nexusapp.api.model;

import javax.persistence.*;

@Entity
@Table(name = "tb_seq_conta")
@SequenceGenerator(name = "sq_seq_conta_generator", sequenceName = "sq_seq_conta", allocationSize = 1)
public class SeqConta {
    @Id
    @Column(name = "id_seq_conta")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_seq_conta_generator")
    private Long id;
    
    @Column(name = "nr_ano", nullable = false)
    private Long nrAno;
    
    @Column(name = "nr_sequencial", nullable = false)
    private Long nrSequencial;

    @Column(name = "fk_conta", nullable = false)
    private Long idConta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNrAno() {
        return nrAno;
    }

    public void setNrAno(Long nrAno) {
        this.nrAno = nrAno;
    }

    public Long getNrSequencial() {
        return nrSequencial;
    }

    public void setNrSequencial(Long nrSequencial) {
        this.nrSequencial = nrSequencial;
    }

    public Long getIdConta() {
        return idConta;
    }

    public void setIdConta(Long idConta) {
        this.idConta = idConta;
    }
}