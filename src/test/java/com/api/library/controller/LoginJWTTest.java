package com.api.library.controller;

import com.api.library.model.Usuario;
import com.api.library.repository.UsuarioRepository;
import com.api.library.security.WebSecurityConfig;
import com.api.library.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = { WebSecurityConfig.class })
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginJWTTest {

    private String URL = "/login";

    private String HEADER_STRING = "Authorization";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    @DisplayName("Deve retornar um JWT Token de autenticação para Usuario existente.")
    public void obterJWTTokenValido() throws Exception {

        boolean existe = true;
        String loginJSON = obterJSONLogin(existe);
        Usuario usuarioSalvo = getUsuario();

        BDDMockito.given(userDetailsServiceImpl.loadUserByUsername(usuarioSalvo.getEmail()))
                .willReturn(usuarioSalvo);
        
        MvcResult result = mockMvc.perform(post(URL)
                .content(loginJSON))

                .andExpect(status().isOk())
                .andDo(print()).andReturn();

        String tokenJSON = result.getResponse().getContentAsString();

        assertThat(tokenJSON).isNotBlank();
        assertThat(tokenJSON).contains(HEADER_STRING);
    }

    @Test
    @DisplayName("Deve retornar erro 401(Não autorizado) ao tentar logar com Usuario Inexistente.")
    public void retornarErroUsuarioInexistente() throws Exception {

        boolean existe = false;
        String loginJSON = obterJSONLogin(existe);
        String email = "email@inexistente.com";

        BDDMockito.given(userDetailsServiceImpl.loadUserByUsername(email))
                .willReturn(null);
        
        mockMvc.perform(post(URL)
                .content(loginJSON))

                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    private String obterJSONLogin(boolean existe)  {

        String email = "adn@gmail.com";
        String senha = "123";

        if(!existe) {
            email = "email@inexistente.com";
            senha = "1234";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("senha", senha);

        String loginJSON = "";

        try {
            loginJSON = new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
        }

        return loginJSON;
    }

    private Usuario getUsuario() {
        return new Usuario(100l, "Anderson Test", "adn@gmail.com",
                 new BCryptPasswordEncoder().encode("123"));
    }

}