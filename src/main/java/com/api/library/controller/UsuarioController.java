package com.api.library.controller;

import com.api.library.dto.UsuarioRequestDTO;
import com.api.library.dto.UsuarioResponseDTO;
import com.api.library.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Tag(name = "Usuarios", description = "Endpoins para recursos de usuarios")
@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @Operation(summary = "Buscar um usuario por Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Nenhum usuario encontrado!")
    })
    @GetMapping("/{id}")
    private ResponseEntity<UsuarioResponseDTO> obterPorId(@Parameter(description = "Id do usuario a buscar")
                                                              @PathVariable("id") Long id) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.obterPorId(id);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @Operation(summary = "Salvar um usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario salvo"),
            @ApiResponse(responseCode = "400", description = "Erro ao informar dados de usuario"),
            @ApiResponse(responseCode = "409", description = "Dado de usuario já cadastrado")})
    @PostMapping
    private ResponseEntity<UsuarioResponseDTO> salvar(@Parameter(name = "Usuario a salvar", description = "Usuario com dados a salvar", required = true)
                                                          @RequestBody @Valid UsuarioRequestDTO usuarioRequest,
                                                        UriComponentsBuilder uriBuilder) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.salvar(usuarioRequest);
        UriComponents enderecoUsuarioSalvo = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuarioResponseDTO.getId());

        return ResponseEntity.created(enderecoUsuarioSalvo.toUri()).body(usuarioResponseDTO);
    }
}