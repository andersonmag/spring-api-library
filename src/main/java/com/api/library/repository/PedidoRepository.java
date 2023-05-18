package com.api.library.repository;

import java.util.List;
import java.util.Optional;

import com.api.library.model.Pedido;
import com.api.library.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByCodigo(int codigo);
    List<Pedido> findByUsuario(Usuario usuario);
}