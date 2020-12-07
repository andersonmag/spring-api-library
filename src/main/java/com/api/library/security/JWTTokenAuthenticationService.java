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

    // configurações iniciais
    @Value("${expiration-time}")
    private static long EXPIRATION_TIME = 172800000;
    @Value("${secret-key}")
    private static final String SK = "07yu3SFdFVPYck4VLy5lXpAKSHyZF4rgTUbGd2_yhHA1BfCoc0HzUpLryfugxZilnkDNK9aFVgUSKF7NnxyFs14tNFq98GHLfr1mGtqCdA8AU5R1xvKGvQ-SWqAO1a2GBuaYPYTAUY0UierJsv4A3XxdWXh5RTM0olXCkiSnQ6oYfQGiwOaH_XNz0mQ5OaPP5ybYRL5vCschNqX9kr9VVV4p2Tjv1YcDUmFhKQTCVj-8wBEO2Y4brP_fs5b4JX6-3L-BtfQbmr4lELt3rY0YPt5vPrJVZLzAmAQbHDV0rRtpG6Vc1NemuW-bjcbi9pVlEkdhTEqt_CtZZRDbnB0i6w";

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

        response.addHeader(HEADER_STRING, tokenFinal);
        
        response = liberacaoCors(response);
        
        response.getWriter().write("{\"" + HEADER_STRING + "\": \"" + tokenFinal + "\"}");
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
                                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                                .signWith(SignatureAlgorithm.HS512, SK).compact();

        return TOKEN_PREFIX + " " + tokenValue;
    }
}