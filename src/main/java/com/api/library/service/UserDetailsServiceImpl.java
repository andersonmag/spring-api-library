package com.api.library.service;

import java.util.Optional;
import com.api.library.model.Usuario;
import com.api.library.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent())
            return new User(usuarioOptional.get().getEmail(), 
                            usuarioOptional.get().getPassword(),
                            usuarioOptional.get().getAuthorities());
        throw new UsernameNotFoundException("Usuario não encontrado!");
    }
}