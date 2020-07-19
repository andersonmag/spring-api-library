package com.api.library.controller;

import com.api.library.model.Usuario;
import com.api.library.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    static String BASE_URL = "/usuarios";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    @DisplayName(value = "Test Mapeamento para Salvar novo Usuario. Deve retornar Status(201) e Usuario Salvo.")
    public void salvarUsuario() throws Exception {

        String usuarioJSON = new ObjectMapper().writeValueAsString(criarUsuario());

        BDDMockito.given(usuarioService.salvar(Mockito.any(Usuario.class)))
                .willReturn(criarUsuario());

        mockMvc.perform(MockMvcRequestBuilders
                                        .post(BASE_URL)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(usuarioJSON))
                .andDo(print())

                .andExpect(status().isCreated())
                .andExpect(jsonPath("email").isNotEmpty());
    }

    @Test
    @DisplayName(value = "Test Mapeamento para obter Usuario por ID. Deve retornar Status(200) e Usuario buscado.")
    public void obterUsuarioPorId() throws Exception {

        Long id = 1l;
        Usuario usuario = criarUsuario();
        usuario.setId(id);
        
        BDDMockito.given(usuarioService.obterPorId(id))
                    .willReturn(Optional.of(usuario));

        mockMvc.perform(MockMvcRequestBuilders
                                        .get(BASE_URL.concat("/" + id)))
                .andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("email").isNotEmpty());
    }

    private Usuario criarUsuario() {
        return new Usuario("Anderson",
                           "adn@gmail.com",
                           "$2a$10$Hx93oG6m1QwLOcYJFyAOheBTOa7EefgqJ.oXAn2JIzj6DW8Y5lIIW",
                            Boolean.TRUE);
    }
}
