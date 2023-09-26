package com.lucasricardo.people.mappers;

import com.lucasricardo.people.dtos.PessoaDTO;
import com.lucasricardo.people.entities.PessoaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    @Mapping(target = "enderecos", ignore = true)
    PessoaDTO toDTO(PessoaEntity pessoaEntity);

    PessoaEntity toEntity(PessoaDTO pessoaDTO);
}

