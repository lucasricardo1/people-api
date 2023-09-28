package com.lucasricardo.people.controllers;

import com.lucasricardo.people.dtos.EnderecoDTO;
import com.lucasricardo.people.dtos.PessoaDTO;
import com.lucasricardo.people.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas/{idPessoa}/enderecos")
public class EnderecoController {


    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
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
    public ResponseEntity<List<EnderecoDTO>> consultarEnderecos(@PathVariable Long idPessoa) {
        List<EnderecoDTO> enderecos = enderecoService.consultarEnderecos(idPessoa);
        return ResponseEntity.ok(enderecos);
    }

}
