package com.api.library.service;

import com.api.library.exception.LivroNotFoundException;
import com.api.library.model.Categoria;
import com.api.library.model.Livro;
import com.api.library.repository.LivroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
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
            new LivroNotFoundException("Livro não encontrado!"));
    }

    public Livro obterPorId(Long id) {
        return livroRepository.findById(id).orElseThrow(() ->
             new LivroNotFoundException("Livro não encontrado!"));
    }

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public void excluir(Livro livro) {
        livroRepository.delete(livro);
    }
}