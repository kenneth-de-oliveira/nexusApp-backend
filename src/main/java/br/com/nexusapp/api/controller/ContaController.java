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
@RequestMapping(value = "/contas", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ContaController {

    private final IContaService contaService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ContaController(
            IContaService contaService,
            ApplicationEventPublisher eventPublisher) {
        this.contaService = contaService;
        this.eventPublisher = eventPublisher;
    }

    @ResponseBody
    @GetMapping("/buscar-por-numero/{numero}")
    public ResponseEntity<ContaFullDTO> buscarContaPorNumero(
        @PathVariable final String numero){
        return ResponseEntity.ok(
        contaService.buscarContaPorNumero(numero));
    }

    @ResponseBody
    @GetMapping("/buscar-por-agencia/{agencia}")
    public ResponseEntity<ContaFullDTO> buscarContaAgencia(
        @PathVariable final String agencia){
        return ResponseEntity.ok(
        contaService.buscarContaAgencia(agencia));
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<ContaFullDTO> buscarContaPorId(
        @PathVariable final Long id){
        return ResponseEntity.ok(
        contaService.buscarContaPorId(id));
    }

    @ResponseBody
    @PostMapping("/cadastrar")
    public ResponseEntity<ContaFullDTO> cadastrar(
        @RequestBody @Valid ContaDTO contaDto,
        HttpServletResponse response) {
        var contaDTO = contaService.cadastrar(contaDto);
        eventPublisher.publishEvent(new RecursoCriadoEvent(this, response, contaDTO.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(contaDTO);
    }
}
