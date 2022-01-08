package br.com.nexusapp.api.repository;

import br.com.nexusapp.api.model.SeqAgencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeqAgenciaRepository extends JpaRepository<SeqAgencia, Long> {
    SeqAgencia findByIdAndNrAno(Long idConta, Long nrAno);
}