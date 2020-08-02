package com.api.library.service;

import java.util.List;
import java.util.Optional;
import com.api.library.model.Usuario;
import com.api.library.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public List<Usuario> obterTodos() {
        return this.usuarioRepository.findAll();
    }

    public Optional<Usuario> obterPorId(Long id) {
       return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void excluir(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }


}