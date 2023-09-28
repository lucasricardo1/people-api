package com.lucasricardo.people.services;

import com.lucasricardo.people.dtos.PessoaDTO;
import com.lucasricardo.people.entities.PessoaEntity;
import com.lucasricardo.people.mappers.EnderecoMapper;
import com.lucasricardo.people.mappers.PessoaMapper;
import com.lucasricardo.people.repositories.EnderecoRepository;
import com.lucasricardo.people.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository,EnderecoRepository enderecoRepository ,PessoaMapper pessoaMapper, EnderecoMapper enderecoMapper) {
        this.pessoaMapper = pessoaMapper;
        this.pessoaRepository = pessoaRepository;
    }

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

    public List<PessoaDTO> consultarPessoas(){
        return pessoaMapper.listToDTO(pessoaRepository.findAll());
    }

}
