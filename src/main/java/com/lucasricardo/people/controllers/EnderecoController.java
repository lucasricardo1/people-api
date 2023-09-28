package com.lucasricardo.people.controllers;

import com.lucasricardo.people.dtos.EnderecoDTO;
import com.lucasricardo.people.dtos.PessoaDTO;
import com.lucasricardo.people.services.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas/{idPessoa}/enderecos")
@Tag(name = "Endereços", description = "Operações relacionadas ao endereço")
public class EnderecoController {


    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    @Operation(description = "Criar Endereço para uma Pessoa")
    public ResponseEntity<PessoaDTO> criarEnderecoParaPessoa(
            @PathVariable Long idPessoa,
            @RequestBody EnderecoDTO enderecoDTO
    ) {
        PessoaDTO pessoaComEndereco = enderecoService.criarEnderecoParaPessoa(idPessoa, enderecoDTO);
        if (pessoaComEndereco.getId() != null) {
            return ResponseEntity.ok(pessoaComEndereco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(description = "Consutlar Endereços de uma Pessoa")
    public ResponseEntity<List<EnderecoDTO>> consultarEnderecos(@PathVariable Long idPessoa) {
        List<EnderecoDTO> enderecos = enderecoService.consultarEnderecos(idPessoa);
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/favorito")
    @Operation(description = "Consultar Endereço favorito de uma Pessoa")
    public ResponseEntity<EnderecoDTO> consultarEnderecoFavorito(@PathVariable Long idPessoa) {
        EnderecoDTO endereco = enderecoService.consultarEnderecoFavorito(idPessoa);
        return ResponseEntity.ok(endereco);
    }

}
