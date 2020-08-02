package com.api.library.security;

import com.api.library.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Component
@Configuration
@EnableWebMvc
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    private UserDetailsServiceImpl userDetails;

    public WebSecurityConfig(UserDetailsServiceImpl userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
        .headers().frameOptions().disable() //configuração pro h2 funfar
        .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .disable().authorizeRequests()
        .antMatchers("/**").permitAll()
        .and().logout().logoutSuccessUrl("/")
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

        .and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                                UsernamePasswordAuthenticationFilter.class)
        
        .addFilterBefore(new JWTApiAuthentication(), UsernamePasswordAuthenticationFilter.class);
    }


}