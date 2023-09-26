package com.lucasricardo.people.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PessoaDTO {
    private Long id;
    private String nome;
    private String dataNascimento;
    private List<EnderecoDTO> enderecos;
}
