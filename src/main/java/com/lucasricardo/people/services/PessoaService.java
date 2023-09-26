package com.lucasricardo.people.services;

import com.lucasricardo.people.dtos.EnderecoDTO;
import com.lucasricardo.people.dtos.PessoaDTO;
import com.lucasricardo.people.entities.PessoaEntity;
import com.lucasricardo.people.mappers.EnderecoMapper;
import com.lucasricardo.people.mappers.PessoaMapper;
import com.lucasricardo.people.repositories.EnderecoRepository;
import com.lucasricardo.people.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    PessoaMapper pessoaMapper;

    @Autowired
    EnderecoMapper enderecoMapper;

    public PessoaDTO criarPessoa(PessoaDTO pessoaDTO){
        return pessoaMapper.toDTO(pessoaRepository.save(pessoaMapper.toEntity(pessoaDTO)));
    }

    public PessoaDTO editarPessoa(PessoaDTO pessoaDTO){
        Optional<PessoaEntity> pessoaEntity = pessoaRepository.findById(pessoaDTO.getId());

        if(pessoaEntity.isPresent()){
            pessoaEntity.get().setNome(pessoaDTO.getNome());
            pessoaEntity.get().setDataNascimento(pessoaDTO.getDataNascimento());

            return pessoaMapper.toDTO(pessoaRepository.save(pessoaEntity.get()));

        }
        else return new PessoaDTO();
    }

    public PessoaDTO consultarPessoa(Long id){
        return pessoaRepository.findById(id).isPresent() ?
                pessoaMapper.toDTO(pessoaRepository.findById(id).get()) : new PessoaDTO() ;
    }

    public PessoaDTO criarEnderecoParaPessoa(Long idPessoa, EnderecoDTO enderecoDTO){
        if(pessoaRepository.findById(idPessoa).isPresent()){

            PessoaEntity pessoaEntity = pessoaRepository.findById(idPessoa).get();
            pessoaEntity.getEnderecos().add(enderecoMapper.toEntity(enderecoDTO));

            return pessoaMapper.toDTO(pessoaRepository.save(pessoaEntity));

        } else return new PessoaDTO();
    }

    public List<EnderecoDTO> consultarEnderecos(Long idPessoa){
        return pessoaRepository.findById(idPessoa).isPresent() ?
                enderecoMapper.toListDTO(pessoaRepository.findById(idPessoa).get().getEnderecos()) : new ArrayList<>();
    }
}
