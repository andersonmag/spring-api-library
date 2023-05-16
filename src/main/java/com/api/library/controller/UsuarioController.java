package com.api.library.controller;

import com.api.library.dto.UsuarioDTO;
import com.api.library.dto.UsuarioResponseDTO;
import com.api.library.model.Pedido;
import com.api.library.model.Usuario;
import com.api.library.service.EmailService;
import com.api.library.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    private final ModelMapper modelMapper;

    private final EmailService emailService;

    @GetMapping("/enviar-email")
    public ResponseEntity<HttpStatus> enviarEmail() {
        try {
            emailService.enviarEmail("Envio de e-mail normal",
                                     "emaildestino@gmail.com",
                                      "Esse e-mail é de envio normal.");
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            
            return ResponseEntity.badRequest().build();
        }
    }

    @Scheduled(cron = "0 20 10 1/1 * *")
    public void agendarEnvioEmail() {
        System.err.println("Esse e-mail foi agendado para agr: " + LocalDateTime.now());
    }

    @GetMapping("/{id}")
    private ResponseEntity<UsuarioResponseDTO> obterPorId(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.obterPorId(id);

        if (usuarioOptional.isPresent()){
            UsuarioResponseDTO usuarioDTO = converterParaUsuarioDTO(usuarioOptional.get());
            return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    private ResponseEntity<UsuarioResponseDTO> salvar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuarioConvertido = converterParaUsuario(usuarioDTO);
        Usuario usuario = usuarioService.salvar(usuarioConvertido);

        UsuarioResponseDTO usuarioResDTO = modelMapper.map(usuario, UsuarioResponseDTO.class);

        return new ResponseEntity<>(usuarioResDTO, HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<List<UsuarioResponseDTO>> obterTodos() {
        List<Usuario> usuarios = usuarioService.obterTodosDTO();
        List<UsuarioResponseDTO> usuariosDTO = converterParaListUsuarioDTO(usuarios);

        return new ResponseEntity<>(usuariosDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    private ResponseEntity<?> mudarStatus(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.obterPorId(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.isStatus())
                usuario.setStatus(false);
            else
                usuario.setStatus(true);

            usuarioService.salvar(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/pedidos")
    private ResponseEntity<List<Pedido>> obterPedidosUsuario(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.obterPorId(id);
        if (usuarioOptional.isPresent())
            return new ResponseEntity<>(usuarioOptional.get().getPedidos(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
}