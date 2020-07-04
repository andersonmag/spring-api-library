package com.api.library.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import com.api.library.model.Pedido;
import com.api.library.model.Usuario;
import com.api.library.repository.PedidoRepository;
import com.api.library.repository.UsuarioRepository;
import com.api.library.service.PedidoService;

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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/{id}")
    private ResponseEntity<Usuario> get(@PathVariable("id") Long id) {
        return new ResponseEntity<>(usuarioRepository.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Pedido> create(@RequestBody @Valid Pedido pedido) {
        return new ResponseEntity<>(pedidoService.salvar(pedido), HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<Pedido>> getAll() {
        return new ResponseEntity<>(pedidoRepository.findAll(), HttpStatus.OK);
    }


    @PatchMapping("/{id}/status")
    private ResponseEntity<?> mudarStatus(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.isStatus())
                usuario.setStatus(false);
            else
                usuario.setStatus(true);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}