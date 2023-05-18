package com.api.library.controller;

import com.api.library.dto.UsuarioDTO;
import com.api.library.dto.UsuarioResponseDTO;
import com.api.library.model.Pedido;
import com.api.library.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

//    private final EmailService emailService;

//    @GetMapping("/enviar-email")
//    public ResponseEntity<HttpStatus> enviarEmail() {
//        try {
//            emailService.enviarEmail("Envio de e-mail normal",
//                                     "emaildestino@gmail.com",
//                                      "Esse e-mail é de envio normal.");
//            return ResponseEntity.ok(HttpStatus.OK);
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @Scheduled(cron = "0 20 10 1/1 * *")
//    public void agendarEnvioEmail() {
//        System.err.println("Esse e-mail foi agendado para agr: " + LocalDateTime.now());
//    }

    @GetMapping("/{id}")
    private ResponseEntity<UsuarioResponseDTO> obterPorId(@PathVariable("id") Long id) {
        UsuarioResponseDTO usuarioDTO = usuarioService.obterPorId(id);
        return ResponseEntity.ok(usuarioDTO);
    }

    @PostMapping
    private ResponseEntity<UsuarioResponseDTO> salvar(@RequestBody @Valid UsuarioDTO usuarioDTO, UriComponentsBuilder uriBuilder) {
        UsuarioResponseDTO usuarioResponseDto = usuarioService.salvar(usuarioDTO);
        UriComponents enderecoUsuarioSalvo = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuarioResponseDto.getId());

        return ResponseEntity.created(enderecoUsuarioSalvo.toUri()).body(usuarioResponseDto);
    }

    @GetMapping
    private ResponseEntity<List<UsuarioResponseDTO>> obterTodos() {
        List<UsuarioResponseDTO> usuariosDTO = usuarioService.obterTodosSomenteNomeEmail();
        return ResponseEntity.ok(usuariosDTO);
    }

//    @PatchMapping("/{id}/status")
//    private ResponseEntity<?> mudarStatus(@PathVariable("id") Long id) {
//        Optional<Usuario> usuarioOptional = usuarioService.obterPorId(id);
//
//        if (usuarioOptional.isPresent()) {
//            Usuario usuario = usuarioOptional.get();
//            if (usuario.isStatus())
//                usuario.setStatus(false);
//            else
//                usuario.setStatus(true);
//
//            usuarioService.salvar(usuario);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping("/{id}/pedidos")
    private ResponseEntity<List<Pedido>> obterPedidosUsuario(@PathVariable("id") Long id) {
        List<Pedido> pedidosUsuario = usuarioService.obterPedidosUsuario(id);

        if (pedidosUsuario.isEmpty())
                return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pedidosUsuario);
    }

}