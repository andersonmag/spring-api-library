package com.api.library.security;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AutenticaJwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    public AutenticaJwtTokenFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(jwtTokenService.getHeaderToken());

        if (token != null) {
            try {
                Authentication autenticado = jwtTokenService.validaRequestToken(token, response);
                SecurityContextHolder.getContext().setAuthentication(autenticado);
            } catch (JwtException exception) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }

        filterChain.doFilter(request, response);
    }
}