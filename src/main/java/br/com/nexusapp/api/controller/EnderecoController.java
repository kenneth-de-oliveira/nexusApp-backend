package br.com.nexusapp.api.controller;

import br.com.nexusapp.api.dtos.EnderecoDTO;
import br.com.nexusapp.api.service.IEnderecoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/enderecos", produces = {MediaType.APPLICATION_JSON_VALUE})
public class EnderecoController {

    private final IEnderecoService enderecoService;

    public EnderecoController(IEnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @ResponseBody
    @PostMapping("/cadastrar")
    public ResponseEntity<EnderecoDTO> cadastrar(
        @RequestParam Long idCliente,
        @RequestBody @Valid EnderecoDTO enderecoDTO,
        HttpServletResponse response) {
        var enderecoDto = enderecoService.cadastrar(idCliente, enderecoDTO);
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
        .buildAndExpand(enderecoDto.getUf()).toUri();
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(enderecoDto);
    }

    @ResponseBody
    @GetMapping("/lista-por-cpf/{cpf}")
    public ResponseEntity<List<EnderecoDTO>> listaDoClientePorCpf(
        @PathVariable final String cpf){
        return ResponseEntity.ok(
        enderecoService.listaDoClientePorCpf(cpf));
    }

}
