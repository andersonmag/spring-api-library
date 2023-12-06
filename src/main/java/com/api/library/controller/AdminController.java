package com.api.library.controller;

import com.api.library.dto.UsuarioResponseDTO;
import com.api.library.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "Endpoins para recursos de admin")
@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Buscar todos os usuarios")
    @SecurityRequirement(name = "token-authotization")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios existentes"),
            @ApiResponse(responseCode = "403", description = "Sem permissão")
    })
    @GetMapping("/usuarios")
    private ResponseEntity<List<UsuarioResponseDTO>> obterTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.obterTodosUsuariosResponseDTO();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Alterar status de usuario")
    @SecurityRequirement(name = "token-authotization")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alteração realizada!"),
            @ApiResponse(responseCode = "403", description = "Sem permissão")
    })
    @PatchMapping("/usuarios/{id}/status")
    private ResponseEntity<?> mudarStatus(@Parameter(description = "Id do usuario") @PathVariable("id") Long id) {
        usuarioService.mudarStatusUsuario(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
