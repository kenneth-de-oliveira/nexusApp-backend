package br.com.nexusapp.api.service;

import br.com.nexusapp.api.dtos.AgenciaDTO;

public interface IAgenciaService {
    AgenciaDTO buscarAgenciaPorId(Long id);
}