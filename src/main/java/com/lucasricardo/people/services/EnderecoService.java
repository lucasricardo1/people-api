package com.lucasricardo.people.services;

import com.lucasricardo.people.dtos.EnderecoDTO;
import com.lucasricardo.people.dtos.PessoaDTO;
import com.lucasricardo.people.entities.EnderecoEntity;
import com.lucasricardo.people.entities.PessoaEntity;
import com.lucasricardo.people.mappers.EnderecoMapper;
import com.lucasricardo.people.mappers.PessoaMapper;
import com.lucasricardo.people.repositories.EnderecoRepository;
import com.lucasricardo.people.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnderecoService {

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    private final EnderecoMapper enderecoMapper;

    @Autowired
    public EnderecoService(PessoaRepository pessoaRepository,EnderecoRepository enderecoRepository ,PessoaMapper pessoaMapper, EnderecoMapper enderecoMapper) {
        this.pessoaMapper = pessoaMapper;
        this.enderecoMapper = enderecoMapper;
        this.pessoaRepository = pessoaRepository;
    }

    public PessoaDTO criarEnderecoParaPessoa(Long idPessoa, EnderecoDTO enderecoDTO){
        if(pessoaRepository.findById(idPessoa).isPresent()){

            PessoaEntity pessoaEntity = pessoaRepository.findById(idPessoa).get();

            if(enderecoDTO.isFavorito()){
                pessoaEntity.getEnderecos().forEach(endereco -> endereco.setFavorito(false));
            }

            pessoaEntity.getEnderecos().add(enderecoMapper.toEntity(enderecoDTO));

            return pessoaMapper.toDTO(pessoaRepository.save(pessoaEntity));

        } else return new PessoaDTO();
    }

    public List<EnderecoDTO> consultarEnderecos(Long idPessoa){
        return pessoaRepository.findById(idPessoa).isPresent() ?
                enderecoMapper.toListDTO(pessoaRepository.findById(idPessoa).get().getEnderecos()) : new ArrayList<>();
    }

}
