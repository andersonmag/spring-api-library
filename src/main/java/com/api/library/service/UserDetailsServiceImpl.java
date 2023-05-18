package com.api.library.service;

import com.api.library.model.Usuario;
import com.api.library.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

     public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
         this.usuarioRepository = usuarioRepository;
     }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado!"));

            return new User(usuario.getEmail(),
                            usuario.getPassword(),
                            usuario.getAuthorities());
    }


}