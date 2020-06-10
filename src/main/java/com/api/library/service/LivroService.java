package com.api.library.service;

import com.api.library.model.Livro;
import com.api.library.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Page<Livro> obterTodos(Pageable pageable, String titulo) {
        
        if (titulo != null)
            return livroRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        return livroRepository.findAll(pageable);
    }
}