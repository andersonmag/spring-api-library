package com.api.library.service;

import com.api.library.dto.LivroRequestDTO;
import com.api.library.exception.RecursoNotFoundException;
import com.api.library.model.Categoria;
import com.api.library.model.Livro;
import com.api.library.repository.LivroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final ModelMapper modelMapper;

    public LivroService(LivroRepository livroRepository, ModelMapper modelMapper) {
        this.livroRepository = livroRepository;
        this.modelMapper = modelMapper;
    }

    public Page<Livro> obterTodos(Pageable pageable, String titulo) {

        if (titulo != null)
            return livroRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        return livroRepository.findAll(pageable);
    }

    public Page<Livro> obterPorCategoria(Categoria categoria, Pageable pageable) {
        return livroRepository.findByCategoria(categoria, pageable);
    }

    public Livro obterPorLink(String link) {
        return livroRepository.findByLink(link).orElseThrow(() ->
                new RecursoNotFoundException("Livro não encontrado!"));
    }

    public Livro obterPorId(Long id) {
        return livroRepository.findById(id).orElseThrow(() ->
                new RecursoNotFoundException("Livro não encontrado!"));
    }

    public Livro atualizar(Long id, LivroRequestDTO livroAlterado) {

        if(livroRepository.existsById(id)) {
            throw new RecursoNotFoundException("Livro não encontrado!");
        }

        Livro livroConvertido = modelMapper.map(livroAlterado, Livro.class);
        livroConvertido.setId(id);
        livroConvertido.setDataAtualizacao(LocalDateTime.now());

        return livroRepository.save(livroConvertido);
    }

    public Livro salvar(LivroRequestDTO livroDTO) {
        Livro livroConvertido = modelMapper.map(livroDTO, Livro.class);
        livroConvertido.setDataCriacao(LocalDateTime.now());
        return livroRepository.save(livroConvertido);
    }

    public void excluir(Long id) {
        Livro livro = obterPorId(id);
        livroRepository.delete(livro);
    }
}