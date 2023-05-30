package com.api.library.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class AutenticaJwtTokenFilter extends GenericFilterBean {

    private final JwtTokenService jwtTokenService;

    public AutenticaJwtTokenFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token =  ((HttpServletRequest) request).getHeader(jwtTokenService.getHeaderToken());

        if(token != null) {
            Authentication autenticado = jwtTokenService.validaRequestToken(token, (HttpServletResponse) response);
            SecurityContextHolder.getContext().setAuthentication(autenticado);
        }
        chain.doFilter(request, response);
    }
}