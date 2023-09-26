package com.lucasricardo.people.mappers;

import com.lucasricardo.people.dtos.EnderecoDTO;
import com.lucasricardo.people.entities.EnderecoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoDTO toDTO(EnderecoEntity enderecoEntity);

    EnderecoEntity toEntity(EnderecoDTO enderecoDTO);

    List<EnderecoDTO> toListDTO(List<EnderecoEntity> enderecos);
}

