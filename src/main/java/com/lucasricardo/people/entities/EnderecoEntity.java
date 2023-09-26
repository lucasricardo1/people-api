package com.lucasricardo.people.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EnderecoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;

}
