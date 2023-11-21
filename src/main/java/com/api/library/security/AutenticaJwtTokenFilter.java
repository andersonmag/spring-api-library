package com.api.library.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AutenticaJwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    public AutenticaJwtTokenFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(jwtTokenService.getHeaderToken());

        if(token != null) {
            Authentication autenticado = jwtTokenService.validaRequestToken(token, response);
            SecurityContextHolder.getContext().setAuthentication(autenticado);
        }
        filterChain.doFilter(request, response);
    }
}