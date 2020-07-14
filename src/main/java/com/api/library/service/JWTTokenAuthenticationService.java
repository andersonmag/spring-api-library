package com.api.library.service;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.api.library.ApplicationContextLoad;
import com.api.library.model.Usuario;
import com.api.library.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTTokenAuthenticationService {

    @Value("${expiration-time}")
    private static long EXPIRATION_TIME;
    @Value("${secret-key}")
    private static String SK;

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    public Authentication validTokenUser(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader(HEADER_STRING);

        if (token != null) {

            String email = Jwts.parser()
                               .setSigningKey(SK)
                               .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                               .getBody().getSubject();

            if (email != null) {

                Optional<Usuario> usuaOptional = ApplicationContextLoad
                            .getApplicationContext().getBean(UsuarioRepository.class)
                            .findByEmail(email);

                if (usuaOptional.isPresent()) {
                    Usuario usuario = usuaOptional.get();

                    return new UsernamePasswordAuthenticationToken(usuario.getEmail(),
                                                                   usuario.getSenha(),
                                                                   usuario.getAuthorities());
                }
            }
        }

        response.addHeader("Access-Control-Allow-Origin", "*");
        return null;
    }

    public void getToken(String email, HttpServletResponse response) throws IOException {

        String tokenValue = Jwts.builder()
                                .setSubject(email)
                                .setIssuedAt(new Date(System.currentTimeMillis()))
                                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                                .signWith(SignatureAlgorithm.HS512, SK).compact();

        String tokenFinal = TOKEN_PREFIX + " " + tokenValue;

        response.addHeader(HEADER_STRING, tokenFinal);
        response.addHeader("Acess-Control-Allow-Origin", "*");

        response.getWriter().write("{\"" + HEADER_STRING + "\": \"" + tokenFinal + "\"}");
    }
}