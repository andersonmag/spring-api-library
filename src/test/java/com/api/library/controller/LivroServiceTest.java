package com.api.library.controller;

import com.api.library.dto.LivroRequestDTO;
import com.api.library.model.Livro;
import com.api.library.repository.LivroRepository;
import com.api.library.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class LivroServiceTest {

    private LivroService livroService;
    @Mock
    private LivroRepository livroRepository;

    @BeforeEach
    void setUp() {
        this.livroService = new LivroService(livroRepository, new ModelMapper());
    }

    @DisplayName("Deve adicionar valor antigo caso novo preco seja maior que antigo")
    @Test
    void atualizarLivroComNovoPrecoMenorQueAtual() {
        Long id = 1L;
        LivroRequestDTO livroRequestDTO = new LivroRequestDTO();
        livroRequestDTO.setTitulo("Livro Teste");
        livroRequestDTO.setPreco(BigDecimal.valueOf(13.0));

        Livro livro = new Livro();
        BigDecimal precoAnterior = BigDecimal.valueOf(20.0);
        livro.setPreco(precoAnterior);

        Mockito.when(livroRepository.findById(id)).thenReturn(Optional.of(livro));
        Mockito.when(livroRepository.save(livro)).thenReturn(livro);

        livro = livroService.atualizar(id, livroRequestDTO);

        assertEquals(livro.getPrecoAnterior(), precoAnterior);
        assertEquals(livro.getPreco(), livroRequestDTO.getPreco());
    }

    @DisplayName("Deve atualizar livro com novo preço e definir preço anterior como null")
    @Test
    void atualizarLivroComNovoPrecoMaiorQueAtual() {
        Long id = 1L;
        LivroRequestDTO livroRequestDTO = new LivroRequestDTO();
        livroRequestDTO.setTitulo("Livro Teste");
        livroRequestDTO.setPreco(BigDecimal.valueOf(20.0));

        Livro livro = new Livro();
        BigDecimal precoAnterior = BigDecimal.valueOf(13.0);
        livro.setPreco(precoAnterior);

        Mockito.when(livroRepository.findById(id)).thenReturn(Optional.of(livro));
        Mockito.when(livroRepository.save(livro)).thenReturn(livro);

        livro = livroService.atualizar(id, livroRequestDTO);

        assertEquals(livro.getPrecoAnterior(), null);
        assertEquals(livro.getPreco(), livroRequestDTO.getPreco());
    }
}