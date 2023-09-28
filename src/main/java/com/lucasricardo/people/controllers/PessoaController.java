package com.lucasricardo.people.controllers;

import com.lucasricardo.people.dtos.PessoaDTO;
import com.lucasricardo.people.services.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
@Tag(name = "Pessoas", description = "Operações relacionadas a Pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    @Operation(description= "Criar Pessoa")
    public ResponseEntity<PessoaDTO> criarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO createdPessoa = pessoaService.criarPessoa(pessoaDTO);
        return ResponseEntity.ok(createdPessoa);
    }

    @PutMapping()
    @Operation(description = "Editar Pessoa")
    public ResponseEntity<PessoaDTO> editarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO editedPessoa = pessoaService.editarPessoa(pessoaDTO);
        return ResponseEntity.ok(editedPessoa);
    }

    @GetMapping("/{id}")
    @Operation(description = "Buscar Pessoa por ID")
    public ResponseEntity<PessoaDTO> consultarPessoa(@PathVariable Long id) {
        PessoaDTO pessoa = pessoaService.consultarPessoa(id);
        if (pessoa.getId() != null) {
            return ResponseEntity.ok(pessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    @Operation(description = "Buscar todas as Pessoas")
    public ResponseEntity<List<PessoaDTO>> consultarPessoas() {
        List<PessoaDTO> pessoas = pessoaService.consultarPessoas();
        return ResponseEntity.ok(pessoas);
    }

}
