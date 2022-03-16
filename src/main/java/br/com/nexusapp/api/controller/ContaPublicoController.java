package br.com.nexusapp.api.controller;

import br.com.nexusapp.api.dtos.ContaDTO;
import br.com.nexusapp.api.dtos.ContaFullDTO;
import br.com.nexusapp.api.event.RecursoCriadoEvent;
import br.com.nexusapp.api.service.IContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/publico/contas", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ContaPublicoController {

    private final IContaService contaService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ContaPublicoController(
        IContaService contaService,
        ApplicationEventPublisher eventPublisher) {
        this.contaService = contaService;
        this.eventPublisher = eventPublisher;
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity<ContaFullDTO> cadastrar(
        @RequestBody @Valid ContaDTO contaDto,
        HttpServletResponse response) {
        var contaDTO = contaService.cadastrar(contaDto);
        eventPublisher.publishEvent(new RecursoCriadoEvent(this, response, contaDTO.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(contaDTO);
    }
}