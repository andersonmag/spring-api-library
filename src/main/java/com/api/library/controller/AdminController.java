package com.api.library.controller;

import com.api.library.dto.UsuarioResponseDTO;
import com.api.library.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Tag(name = "Usuarios", description = "Endpoins para recursos de admin")
@RolesAllowed({"ROLE_ADMIN"})
@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Buscar todos os usuarios")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios existentes"),
    })
    @GetMapping("/usuarios")
    private ResponseEntity<List<UsuarioResponseDTO>> obterTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.obterTodosUsuariosResponseDTO();
        return ResponseEntity.ok(usuarios);
    }

    @PatchMapping("/usuarios/{id}/status")
    private ResponseEntity<?> mudarStatus(@PathVariable("id") Long id) {
        usuarioService.mudarStatusUsuario(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
