package com.api.library.service;

import com.api.library.dto.UsuarioRequestDTO;
import com.api.library.dto.UsuarioResponseDTO;
import com.api.library.exception.EmailExistenteException;
import com.api.library.exception.RecursoNotFoundException;
import com.api.library.exception.RoleExistenteUsuarioException;
import com.api.library.model.Role;
import com.api.library.model.Usuario;
import com.api.library.repository.RoleRepository;
import com.api.library.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;


    public List<Usuario> obterTodos() {
        return this.usuarioRepository.findAll();
    }

    public List<UsuarioResponseDTO> obterTodosUsuariosResponseDTO() {
        List<Usuario> usuarios = usuarioRepository.findAllSomenteIdEmailNome();
        List<UsuarioResponseDTO> usuariosDTO = converterParaListUsuarioResponseDTO(usuarios);

        return usuariosDTO;
    }

    public UsuarioResponseDTO obterPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Usuario não encontrado!"));

        UsuarioResponseDTO usuarioDTO = converterParaUsuarioResponseDTO(usuario);
       return usuarioDTO;
    }

    public UsuarioResponseDTO salvar(UsuarioRequestDTO usuarioRequestDto) {

        if(usuarioRepository.existsByEmail(usuarioRequestDto.getEmail())) {
            throw new EmailExistenteException("Email já cadastrado no sistema!");
        }

        Usuario usuarioConvertido = converterParaUsuario(usuarioRequestDto);
        usuarioConvertido.setSenha(new BCryptPasswordEncoder().encode(usuarioRequestDto.getSenha()));

        UsuarioResponseDTO usuarioResponse = modelMapper.map(usuarioRepository.save(usuarioConvertido), UsuarioResponseDTO.class);
        return usuarioResponse;
    }

    public void excluir(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }


    private UsuarioResponseDTO converterParaUsuarioResponseDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    private List<UsuarioResponseDTO> converterParaListUsuarioResponseDTO(List<Usuario> usuarios) {
        return  usuarios.stream().map(usuario -> modelMapper.map(usuario, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
    }

    private Usuario converterParaUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        return new Usuario(usuarioRequestDTO);
    }

    @Transactional
    public void mudarStatusUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNotFoundException("Usuario não encontrado!"));

        usuario.setStatus(!usuario.isEnabled());
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void adicionarRoleUsuario(Long usuarioId, Long roleId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNotFoundException("Usuario não encontrado!"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RecursoNotFoundException("Role não encontrada!"));

        boolean usuarioJaPossui = usuario.getRoles().stream().anyMatch(roleIt -> roleIt.getAuthority().equals(role.getAuthority()));
        if(usuarioJaPossui) {
            throw new RoleExistenteUsuarioException("Usuario já possui a role: " + role.getAuthority());
        }

        usuario.getRoles().add(role);
        usuarioRepository.save(usuario);
    }
}