package br.com.nexusapp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexusapp.api.model.Extrato;

public interface ExtratoRepository extends JpaRepository<Extrato, Long> {
}