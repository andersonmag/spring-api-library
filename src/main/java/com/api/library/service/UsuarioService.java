package com.api.library.service;

import com.api.library.dto.UsuarioDTO;
import com.api.library.dto.UsuarioResponseDTO;
import com.api.library.exception.RecursoNaoEncontradoException;
import com.api.library.model.Pedido;
import com.api.library.model.Usuario;
import com.api.library.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }
    
    public List<Usuario> obterTodos() {
        return this.usuarioRepository.findAll();
    }

    public List<UsuarioResponseDTO> obterTodosSomenteNomeEmail() {
        List<Usuario> usuarios = usuarioRepository.findAllOnlyEmailAndNome();
        List<UsuarioResponseDTO> usuariosDTO = converterParaListUsuarioDTO(usuarios);

        return usuariosDTO;
    }

    public UsuarioResponseDTO obterPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario não encontrado!"));

        UsuarioResponseDTO usuarioDTO = converterParaUsuarioDTO(usuario);
       return usuarioDTO;
    }

    public UsuarioResponseDTO salvar(UsuarioDTO usuarioDto) {
        Usuario usuarioConvertido = converterParaUsuario(usuarioDto);
        usuarioConvertido.setSenha(new BCryptPasswordEncoder().encode(usuarioDto.getSenha()));

        UsuarioResponseDTO usuarioResDTO = modelMapper.map(usuarioRepository.save(usuarioConvertido), UsuarioResponseDTO.class);

        return usuarioResDTO;
    }

    public void excluir(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }


    private UsuarioResponseDTO converterParaUsuarioDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    private List<UsuarioResponseDTO> converterParaListUsuarioDTO(List<Usuario> usuarios) {
        return  usuarios.stream().map(usuario -> modelMapper.map(usuario, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
    }

    private Usuario converterParaUsuario(UsuarioDTO usuarioDTO) {
        return new Usuario(usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getSenha());
    }

    public List<Pedido> obterPedidosUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario não encontrado!"));
        return usuario.getPedidos();
    }
}