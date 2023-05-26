package com.api.library.security;

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

    @Value("${jwt-expiration-time}")
    private long TEMPO_EXPIRACAO;
    @Value("${jwt-secret-key}")
    private String SK;
    @Value("${jwt-token-prefix}")
    private String PREFIX_TOKEN;
    private static final String CABECARIO_TOKEN = "Authorization";


    public Authentication validTokenUser(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader(CABECARIO_TOKEN);

        if (token != null) {
            String email = Jwts.parser()
                               .setSigningKey(SK)
                               .parseClaimsJws(token.replace(PREFIX_TOKEN, ""))
                               .getBody().getSubject();

            if (email != null) {
                Optional<Usuario> usuaOptional = ApplicationContextLoad.getApplicationContext()
                                                                       .getBean(UsuarioRepository.class)
                                                                       .findByEmail(email);

                if (usuaOptional.isPresent()) {
                    Usuario usuario = usuaOptional.get();

                    return new UsernamePasswordAuthenticationToken(usuario.getEmail(),
                                                                   usuario.getSenha(),
                                                                   usuario.getAuthorities());
                }
            }
        }

        response = liberacaoCors(response);
        return null;
    }

    public void getToken(String email, HttpServletResponse response) throws IOException {
        String tokenFinal = gerenerateToken(email);

        response.addHeader(CABECARIO_TOKEN, tokenFinal);
        response = liberacaoCors(response);
        
        response.getWriter().write("{\"" + CABECARIO_TOKEN + "\": \"" + tokenFinal + "\"}");
    }

	private HttpServletResponse liberacaoCors(HttpServletResponse response) {

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

    private String gerenerateToken(String email) {
        String tokenValue = Jwts.builder()
                                .setSubject(email)
                                .setIssuedAt(new Date(System.currentTimeMillis()))
                                .setExpiration(new Date(System.currentTimeMillis() + TEMPO_EXPIRACAO))
                                .signWith(SignatureAlgorithm.HS512, SK).compact();

        return PREFIX_TOKEN + " " + tokenValue;
    }
}