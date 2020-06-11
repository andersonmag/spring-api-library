package com.api.library.repository;

import java.util.List;
import java.util.Optional;

import com.api.library.model.Categoria;
import com.api.library.model.Livro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByLink(String link);
    Page<Livro> findByCategoria(Categoria categoria, Pageable pageable);
    Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
}