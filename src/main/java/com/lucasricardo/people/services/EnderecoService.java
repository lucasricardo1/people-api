package com.lucasricardo.people.services;

import com.lucasricardo.people.dtos.EnderecoDTO;
import com.lucasricardo.people.dtos.PessoaDTO;
import com.lucasricardo.people.entities.EnderecoEntity;
import com.lucasricardo.people.entities.PessoaEntity;
import com.lucasricardo.people.exceptions.ApiException;
import com.lucasricardo.people.mappers.EnderecoMapper;
import com.lucasricardo.people.mappers.PessoaMapper;
import com.lucasricardo.people.repositories.EnderecoRepository;
import com.lucasricardo.people.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnderecoService {

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    private final EnderecoMapper enderecoMapper;

    @Autowired
    public EnderecoService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper, EnderecoMapper enderecoMapper){
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

        if(pessoaRepository.findById(idPessoa).isPresent()){
            return enderecoMapper.toListDTO(pessoaRepository.findById(idPessoa).get().getEnderecos());
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(),
                    "Endereços não encontrados com o ID da pessoa: " + idPessoa, HttpStatus.NOT_FOUND);
        }
    }

    public EnderecoDTO consultarEnderecoFavorito(Long idPessoa){

        if(pessoaRepository.findById(idPessoa).isPresent()){
            List<EnderecoDTO> enderecoDTOS =
                    enderecoMapper.toListDTO(pessoaRepository.findById(idPessoa).get().getEnderecos());

            if(enderecoDTOS.isEmpty()){
                throw new ApiException(HttpStatus.NOT_FOUND.toString(),
                        "Endereços não encontrados com o ID da pessoa: " + idPessoa, HttpStatus.NOT_FOUND);
            }

            return enderecoDTOS.stream().filter(end -> end.isFavorito()).findFirst().get();
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND.toString(),
                    "Pessoa não encontrada com o ID: " + idPessoa, HttpStatus.NOT_FOUND);
        }
    }

}
