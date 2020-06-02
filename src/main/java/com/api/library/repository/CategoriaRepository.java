package com.api.library.repository;

import java.util.Optional;

import com.api.library.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    Optional<Categoria> findByLink(String link);
}