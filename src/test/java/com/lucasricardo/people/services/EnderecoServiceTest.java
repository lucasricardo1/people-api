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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnderecoServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PessoaMapper pessoaMapper;

    @Mock
    private EnderecoMapper enderecoMapper;

    @InjectMocks
    private EnderecoService enderecoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarEnderecoParaPessoa() {

        Long idPessoa = 1L;
        PessoaDTO pessoaDTO = new PessoaDTO();
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setFavorito(true);
        List<EnderecoDTO> enderecoDTOS = new ArrayList<>();
        enderecoDTOS.add(enderecoDTO);
        pessoaDTO.setEnderecos(enderecoDTOS);

        EnderecoEntity enderecoEntity = new EnderecoEntity();
        enderecoEntity.setFavorito(true);
        List<EnderecoEntity> enderecoEntities = new ArrayList<>();
        enderecoEntities.add(enderecoEntity);

        PessoaEntity pessoaEntity = new PessoaEntity();
        pessoaEntity.setEnderecos(enderecoEntities);
        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaEntity));
        when(enderecoMapper.toEntity(enderecoDTO)).thenReturn(enderecoEntity);
        when(pessoaRepository.save(pessoaEntity)).thenReturn(pessoaEntity);
        when(pessoaMapper.toDTO(pessoaEntity)).thenReturn(pessoaDTO);

        PessoaDTO result = enderecoService.criarEnderecoParaPessoa(idPessoa, enderecoDTO);

        assertNotNull(result);
        assertTrue(result.getEnderecos().get(0).isFavorito());
    }

    @Test
    public void testConsultarEnderecos() {

        Long idPessoa = 1L;
        List<EnderecoEntity> enderecoEntities = new ArrayList<>();
        enderecoEntities.add(new EnderecoEntity());

        PessoaEntity pessoaEntity = new PessoaEntity();
        pessoaEntity.setEnderecos(enderecoEntities);

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaEntity));
        when(enderecoMapper.toListDTO(enderecoEntities)).thenReturn(new ArrayList<>());

        List<EnderecoDTO> result = enderecoService.consultarEnderecos(idPessoa);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testConsultarEnderecoFavorito() {
        Long idPessoa = 1L;
        List<EnderecoEntity> enderecoEntities = new ArrayList<>();
        EnderecoEntity enderecoFavorito = new EnderecoEntity();
        enderecoFavorito.setFavorito(true);
        enderecoEntities.add(enderecoFavorito);

        PessoaEntity pessoaEntity = new PessoaEntity();
        pessoaEntity.setEnderecos(enderecoEntities);

        List<EnderecoDTO> enderecoDTOS = new ArrayList<>();
        EnderecoDTO enderecoFavoritoDTO = new EnderecoDTO();
        enderecoFavoritoDTO.setFavorito(true);
        enderecoDTOS.add(enderecoFavoritoDTO);

        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setEnderecos(enderecoDTOS);

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaEntity));
        when(enderecoMapper.toListDTO(enderecoEntities)).thenReturn(enderecoDTOS);


        EnderecoDTO result = enderecoService.consultarEnderecoFavorito(idPessoa);

        assertNotNull(result);
        assertTrue(result.isFavorito());
    }

    @Test
    public void testConsultarEnderecoFavoritoPessoaNaoEncontrada() {

        Long idPessoa = 1L;

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class,
                () -> enderecoService.consultarEnderecoFavorito(idPessoa));

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Pessoa não encontrada com o ID: " + idPessoa, exception.getMessage());
    }

    @Test
    public void testCriarEnderecoParaPessoaPessoaNaoEncontrada() {
        Long idPessoa = 1L;
        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.empty());

        assertThrows(ApiException.class,
                () -> enderecoService.criarEnderecoParaPessoa(idPessoa, new EnderecoDTO()));

        verify(pessoaRepository, times(1)).findById(idPessoa);
    }

    @Test
    public void testConsultarEnderecosPessoaNaoEncontrada() {
        Long idPessoa = 1L;
        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.empty());

        assertThrows(ApiException.class,
                () -> enderecoService.consultarEnderecos(idPessoa));

        verify(pessoaRepository, times(1)).findById(idPessoa);
    }

    @Test
    public void testConsultarEnderecoFavoritoEnderecosNaoEncontrados() {
        Long idPessoa = 1L;
        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(new PessoaEntity()));

        assertThrows(ApiException.class,
                () -> enderecoService.consultarEnderecoFavorito(idPessoa));
        verify(pessoaRepository, times(2)).findById(idPessoa);
    }
}

