package com.api.library.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.api.library.dto.UsuarioResponseDTO;
import com.api.library.model.Usuario;
import com.api.library.repository.UsuarioRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public List<Usuario> obterTodosDTO() {
        List<Usuario> usuarios = this.usuarioRepository.findAllOnlyEmailAndNome();
        return usuarios;
    }

    public Optional<Usuario> obterPorId(Long id) {
       return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public void excluir(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

}