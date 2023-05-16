package com.api.library.service;

import com.api.library.model.Usuario;
import com.api.library.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

     public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
         this.usuarioRepository = usuarioRepository;
     }

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