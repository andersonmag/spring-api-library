package com.api.library.service;

import com.api.library.dto.UsuarioRequestDTO;
import com.api.library.dto.UsuarioResponseDTO;
import com.api.library.exception.RecursoNotFoundException;
import com.api.library.model.Pedido;
import com.api.library.model.Usuario;
import com.api.library.repository.PedidoRepository;
import com.api.library.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final PedidoRepository pedidoRepository;


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

    public List<Pedido> obterPedidosUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException("Usuario não encontrado!"));
        return pedidoRepository.findByUsuario(usuario);
    }
}