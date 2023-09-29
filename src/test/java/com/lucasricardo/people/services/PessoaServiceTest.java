package com.lucasricardo.people.services;

import com.lucasricardo.people.dtos.PessoaDTO;
import com.lucasricardo.people.entities.PessoaEntity;
import com.lucasricardo.people.exceptions.ApiException;
import com.lucasricardo.people.mappers.PessoaMapper;
import com.lucasricardo.people.repositories.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PessoaMapper pessoaMapper;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarPessoa() {
        PessoaDTO pessoaDTO = new PessoaDTO();
        PessoaEntity pessoaEntity = new PessoaEntity();

        when(pessoaMapper.toEntity(pessoaDTO)).thenReturn(pessoaEntity);
        when(pessoaRepository.save(pessoaEntity)).thenReturn(pessoaEntity);
        when(pessoaMapper.toDTO(pessoaEntity)).thenReturn(pessoaDTO);

        PessoaDTO result = pessoaService.criarPessoa(pessoaDTO);

        assertNotNull(result);
    }

    @Test
    public void testEditarPessoa() {
        Long idPessoa = 1L;
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(idPessoa);
        PessoaEntity pessoaEntity = new PessoaEntity();

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaEntity));
        when(pessoaRepository.save(pessoaEntity)).thenReturn(pessoaEntity);
        when(pessoaMapper.toDTO(pessoaEntity)).thenReturn(pessoaDTO);

        PessoaDTO result = pessoaService.editarPessoa(pessoaDTO);

        assertNotNull(result);
        assertEquals(idPessoa, result.getId());
    }

    @Test
    public void testEditarPessoaPessoaNaoEncontrada() {
        Long idPessoa = 1L;
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(idPessoa);

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class,
                () -> pessoaService.editarPessoa(pessoaDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Pessoa não encontrada com o ID: " + idPessoa, exception.getMessage());
    }

    @Test
    public void testConsultarPessoa() {
        Long idPessoa = 1L;
        PessoaEntity pessoaEntity = new PessoaEntity();
        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaEntity));
        when(pessoaMapper.toDTO(pessoaEntity)).thenReturn(new PessoaDTO());

        PessoaDTO result = pessoaService.consultarPessoa(idPessoa);

        assertNotNull(result);
    }

    @Test
    public void testConsultarPessoaPessoaNaoEncontrada() {
        Long idPessoa = 1L;
        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class,
                () -> pessoaService.consultarPessoa(idPessoa));

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Pessoa não encontrada com o ID: " + idPessoa, exception.getMessage());
    }

    @Test
    public void testConsultarPessoas() {
        List<PessoaEntity> pessoas = List.of(new PessoaEntity(), new PessoaEntity());
        List<PessoaDTO> pessoaDTOS = List.of(new PessoaDTO(), new PessoaDTO());
        when(pessoaRepository.findAll()).thenReturn(pessoas);
        when(pessoaMapper.listToDTO(pessoas)).thenReturn(pessoaDTOS);

        List<PessoaDTO> result = pessoaService.consultarPessoas();

        assertNotNull(result);
        assertEquals(pessoas.size(), result.size());
    }
}

