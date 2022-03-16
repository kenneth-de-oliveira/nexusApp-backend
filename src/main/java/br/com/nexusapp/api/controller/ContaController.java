package br.com.nexusapp.api.controller;

import br.com.nexusapp.api.dtos.ContaFullDTO;
import br.com.nexusapp.api.dtos.ExtratoDTO;
import br.com.nexusapp.api.dtos.InfoContaDTO;
import br.com.nexusapp.api.dtos.InfoContaFullDTO;
import br.com.nexusapp.api.service.IContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/contas", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ContaController {

    private final IContaService contaService;

    @Autowired
    public ContaController(
        IContaService contaService) {
        this.contaService = contaService;
    }

    @ResponseBody
    @GetMapping("/consultar-saldo/{agencia}/{numero}")
    public ResponseEntity<ContaFullDTO> consultarSaldo(
        @PathVariable final String agencia,
        @PathVariable final String numero){
        return ResponseEntity.ok(
        contaService.consultarSaldo(agencia, numero));
    }

    @ResponseBody
    @GetMapping("/buscar-por-numero/{numero}")
    public ResponseEntity<ContaFullDTO> buscarContaPorNumero(
        @PathVariable final String numero){
        return ResponseEntity.ok(
        contaService.buscarContaPorNumero(numero));
    }
    
    @ResponseBody
    @GetMapping("/buscar-por-cpf/{cpf}")
    public ResponseEntity<ContaFullDTO> buscarContaPorCpf(
        @PathVariable final String cpf){
        return ResponseEntity.ok(
        contaService.buscarContaPorCpf(cpf));
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

    @GetMapping("/extratos")
    public ResponseEntity<List<ExtratoDTO>> extratos(@RequestParam Long idConta) {
        List<ExtratoDTO> extratoDTOS = contaService.listarExtratos(idConta);
        return ResponseEntity.ok(extratoDTOS);
    }

}
