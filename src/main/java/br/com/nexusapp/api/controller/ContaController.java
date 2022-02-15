package br.com.nexusapp.api.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.com.nexusapp.api.dtos.InfoContaFullDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.nexusapp.api.dtos.ContaDTO;
import br.com.nexusapp.api.dtos.ContaFullDTO;
import br.com.nexusapp.api.dtos.InfoContaDTO;
import br.com.nexusapp.api.event.RecursoCriadoEvent;
import br.com.nexusapp.api.service.IContaService;

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

    @ResponseBody
    @PostMapping("/depositar")
    public ResponseEntity<ContaFullDTO> depositar(
        @RequestBody @Valid InfoContaDTO infoContaDTO) {
        contaService.depositar(infoContaDTO);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PostMapping("/sacar")
    public ResponseEntity<ContaFullDTO> sacar(
        @RequestBody @Valid InfoContaDTO infoContaDTO) {
        contaService.sacar(infoContaDTO);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PostMapping("/transferir")
    public ResponseEntity<ContaFullDTO> transferir(
        @RequestBody @Valid InfoContaFullDTO infoContaFullDTO) {
        contaService.transferir(infoContaFullDTO);
        return ResponseEntity.ok().build();
    }

}
