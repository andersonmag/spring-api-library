package com.api.library.controller;

import com.api.library.dto.LoginRequestDTO;
import com.api.library.dto.TokenAutenticacaoResponseDTO;
import com.api.library.security.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "Login", description = "Endpoint para login")
@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager manager;
    private final JwtTokenService jwtTokenService;

    @Operation(summary = "Efetuar login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Credenciais corretas e token retornado"),
            @ApiResponse(responseCode = "403", description = "Credenciais incorretas")
    })
    @PostMapping
    public ResponseEntity efetuarLogin(@Parameter(name = "Email e senha para login", required = true)
                                       @RequestBody @Valid LoginRequestDTO login, HttpServletResponse response) {
        var dadosAutenticacao = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
        var autenticado = manager.authenticate(dadosAutenticacao);
        String tokenGerado = jwtTokenService.gerarToken(autenticado);

        response.addHeader(jwtTokenService.getHeaderToken(), tokenGerado);
        return ResponseEntity.ok(new TokenAutenticacaoResponseDTO(tokenGerado));
    }
}
