package com.api.library.service;

import com.api.library.model.Livro;
import com.api.library.model.LivroPage;
import com.api.library.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public LivroPage obterTodos(Page<Livro> livros) {
        return new LivroPage(
                             livros.getPageable().getPageNumber(),
                             livros.getPageable().getPageSize(), 
                             livros.getTotalElements(),
                             livros.getTotalPages(),
                             livros.getContent());
    }
}