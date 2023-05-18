package com.api.library.service;

import com.api.library.exception.RecursoNaoEncontradoException;
import com.api.library.model.Categoria;
import com.api.library.model.Livro;
import com.api.library.repository.LivroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
                new RecursoNaoEncontradoException("Livro não encontrado!"));
    }

    public Livro obterPorId(Long id) {
        return livroRepository.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Livro não encontrado!"));
    }

    public Livro atualizar(Long id, Livro livroAlterado) {
        Livro livroBuscado = obterPorId(id);
        livroAlterado.setId(id);
        livroAlterado.setDataCriacao(livroBuscado.getDataCriacao());
        livroAlterado.setDataAtualizacao(LocalDateTime.now());

        return salvar(livroAlterado);
    }

    public Livro salvar(Livro livro) {
        livro.setDataCriacao(LocalDateTime.now());
        return livroRepository.save(livro);
    }

    public void excluir(Long id) {
        Livro livro = obterPorId(id);
        livroRepository.delete(livro);
    }
}