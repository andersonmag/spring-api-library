package com.api.library.security;

import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import com.api.library.model.Usuario;
import com.api.library.repository.UsuarioRepository;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@AllArgsConstructor
@Service
public class JwtTokenService {

    private final JwtTokenPropriedades propriedades;
    private final UsuarioRepository usuarioRepository;

    public Authentication validaRequestToken(String token, HttpServletResponse response) throws JwtException {

        String email = null;

        try {
            email = Jwts.parser()
                    .setSigningKey(propriedades.getSecretKey())
                    .parseClaimsJws(token.replace(propriedades.getTokenPrefix(), ""))
                    .getBody().getSubject();

        } catch (JwtException exception) {
            throw exception;
        }

        if (email != null) {
            Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();

                return new UsernamePasswordAuthenticationToken(usuario.getEmail(),
                        usuario.getSenha(),
                        usuario.getAuthorities());
            }
        }

        liberaCors(response);
        return null;
    }

    public String gerarToken(Authentication autenticado) {
        String token = Jwts.builder()
                                .setSubject(autenticado.getName())
                                .setIssuedAt(new Date(System.currentTimeMillis()))
                                .setExpiration(new Date(System.currentTimeMillis() + propriedades.getExpirationTime()))
                                .signWith(SignatureAlgorithm.HS512, propriedades.getSecretKey()).compact();

        String tokenFinal = propriedades.getTokenPrefix() + " " + token;
        return tokenFinal;
    }

    private HttpServletResponse liberaCors(HttpServletResponse response) {

        if (response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }

        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }

        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }

        return response;
    }

    public String getHeaderToken() {
        return propriedades.getHeaderToken();
    }
}