package com.api.library.service;

import java.util.List;

import com.api.library.exception.RecursoNaoEncontradoException;
import com.api.library.model.Categoria;
import com.api.library.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria obterCategoria(String link) {
        return categoriaRepository.findByLink(link)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
    }

    public List<Categoria> obterTodas() {
        return categoriaRepository.findAll();
    }
}