package com.api.library.repository;

import java.util.List;
import java.util.Optional;
import com.api.library.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT " + "new com.api.library.model.Usuario(u.id, u.nome, u.email) " +
    "FROM " + "Usuario u")
    List<Usuario> findAllSomenteIdEmailNome();

    boolean existsByEmail(String email);
}