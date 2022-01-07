package br.com.nexusapp.api.service.impl;

import br.com.nexusapp.api.dtos.AgenciaDTO;
import br.com.nexusapp.api.exception.NotFoundException;
import br.com.nexusapp.api.model.Agencia;
import br.com.nexusapp.api.repository.AgenciaRepository;
import br.com.nexusapp.api.service.IAgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgenciaServiceImpl implements IAgenciaService {

    private final AgenciaRepository repository;
    private final MessageSource ms;

    @Autowired
    public AgenciaServiceImpl(AgenciaRepository repository, MessageSource ms) {
        this.repository = repository;
        this.ms = ms;
    }

    @Override
    public AgenciaDTO buscarAgenciaPorId(Long id) {
        Optional<Agencia> agenciaOpt = repository.findById(id);
        if (agenciaOpt.isEmpty()) {
            throw new NotFoundException(ms.getMessage("cliente.consulta.erro",
        null, LocaleContextHolder.getLocale()));
        }
        return agenciaOpt.get().toDTO();
    }
}