package com.api.library.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import com.api.library.model.Pedido;
import com.api.library.model.Usuario;
import com.api.library.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    private ResponseEntity<Usuario> obterPorId(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.obterPorId(id);

        if (usuarioOptional.isPresent())
            return new ResponseEntity<>(usuarioOptional.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    private ResponseEntity<Usuario> salvar(@RequestBody @Valid Usuario usuario) {
        return new ResponseEntity<>(usuarioService.salvar(usuario), HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<Usuario>> obterTodos() {
        return new ResponseEntity<>(usuarioService.obterTodos(), HttpStatus.OK);
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
}