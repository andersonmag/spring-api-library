package com.api.library.service;

import java.util.List;
import java.util.Optional;
import com.api.library.model.Categoria;
import com.api.library.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria obterCategoria(String link) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findByLink(link);

        if (categoriaOptional.isPresent())
            return categoriaOptional.get();
        return null;
    }

    public List<Categoria> obterTodas() {
        return categoriaRepository.findAll();
    }
}