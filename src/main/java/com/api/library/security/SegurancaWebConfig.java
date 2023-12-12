package com.api.library.security;

import com.api.library.exception.handler.CustomAcessoNaoPermitidoExceptionHandler;
import com.api.library.exception.handler.CustomAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@AllArgsConstructor
@Configuration
public class SegurancaWebConfig {
    
    private final AutenticaJwtTokenFilter autenticaJwtTokenFilter;
    private final UserDetailsService userDetailsService;

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

         http
            .cors()
            .and()
            .csrf()
                 .disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login", "/usuarios").anonymous()

                .antMatchers( "/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/livros").hasAnyRole("SELLER","ADMIN")
                .antMatchers(HttpMethod.PUT, "/livros/**").hasAnyRole("SELLER","ADMIN")
                .antMatchers(HttpMethod.DELETE, "/livros/**").hasAnyRole("SELLER","ADMIN")
                .and()
            .sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
            .exceptionHandling()
                 .accessDeniedHandler(new CustomAcessoNaoPermitidoExceptionHandler())
                 .authenticationEntryPoint(authenticationEntryPoint)
                 .and()
            .addFilterBefore(autenticaJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
         return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurity(){
        return (web) -> web.ignoring().antMatchers("/h2/**");
    }
}