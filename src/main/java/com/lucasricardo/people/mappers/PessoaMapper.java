package com.lucasricardo.people.mappers;

import com.lucasricardo.people.dtos.PessoaDTO;
import com.lucasricardo.people.entities.PessoaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    PessoaDTO toDTO(PessoaEntity pessoaEntity);

    PessoaEntity toEntity(PessoaDTO pessoaDTO);

    List<PessoaDTO> listToDTO(List<PessoaEntity> pessoaEntities);
}

