package com.api.library.repository;

import java.util.List;
import com.api.library.model.Categoria;
import com.api.library.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByLink(String link);
    List<Livro> findByCategoria(Categoria categoria);
}