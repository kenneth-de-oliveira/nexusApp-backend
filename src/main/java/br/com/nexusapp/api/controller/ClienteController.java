package br.com.nexusapp.api.controller;

import br.com.nexusapp.api.dtos.ClienteDTO;
import br.com.nexusapp.api.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/clientes", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ClienteController {
    private final IClienteService clienteService;

    @Autowired
    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(
        @PathVariable final Long id){
        return ResponseEntity.ok(
        clienteService.buscarClientePorId(id));
    }

    @ResponseBody
    @GetMapping("/buscar-por-cpf/{cpf}")
    public ResponseEntity<ClienteDTO> buscarClientePorCpf(
        @PathVariable final String cpf){
        return ResponseEntity.ok(
        clienteService.buscarClientePorCpf(cpf));
    }

    @ResponseBody
    @PostMapping("/cadastrar")
    public ResponseEntity<ClienteDTO> cadastrar(
        @RequestBody @Valid ClienteDTO clienteDto,
        HttpServletResponse response) {
        var clienteDTO = clienteService.cadastrar(clienteDto);
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
        .buildAndExpand(clienteDTO.getId()).toUri();
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(clienteDTO);
    }

    @ResponseBody
    @PatchMapping("/{idCliente}")
    public ResponseEntity<ClienteDTO> update(
            @PathVariable final Long idCliente,
            @RequestBody ClienteDTO clienteDto) {
        var clienteDTO = clienteService.update(idCliente, clienteDto);
        return ResponseEntity.ok(clienteDTO);
    }
}
