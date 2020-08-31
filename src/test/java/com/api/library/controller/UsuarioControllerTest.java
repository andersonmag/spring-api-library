package com.api.library.controller;

import com.api.library.ApplicationContextLoad;
import com.api.library.dto.UsuarioDTO;
import com.api.library.dto.UsuarioResponseDTO;
import com.api.library.model.Pedido;
import com.api.library.model.Usuario;
import com.api.library.repository.UsuarioRepository;
import com.api.library.security.WebSecurityConfig;
import com.api.library.service.EmailService;
import com.api.library.service.UserDetailsServiceImpl;
import com.api.library.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UsuarioController.class)
@ContextConfiguration(classes = { WebSecurityConfig.class, UserDetailsServiceImpl.class, UsuarioController.class,
        HttpServletRequest.class, HttpServletResponse.class, UsuarioRepository.class, ApplicationContextLoad.class })
@ActiveProfiles("test")
public class UsuarioControllerTest {

    private String BASE_URL = "/usuarios";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private ApplicationContext context;

    @MockBean
    private UserDetailsServiceImpl implm;

    @MockBean
    private EmailService emailService;

    // private String obterJSONLogin()  {
        
    //     Map<String, Object> map = new HashMap<>();
    //     map.put("email", "adn@gmail.com");
    //     map.put("senha", "123");

    //     String loginJSON = "";
    //     try {
    //         loginJSON = new ObjectMapper().writeValueAsString(map);
    //     } catch (JsonProcessingException e) {
    //         System.err.println(e.getMessage());
    //     }

    //     return loginJSON;
    // }
    
    // private String formatarToken(String tokenJSON) {
    //     System.err.println("pasosu aq");
    //     String token = tokenJSON.replace("{\"Authorization\": \"", "");
    //     token = token.replace("\"}", "");

    //     return token;
    // }

    // @Test
    // @DisplayName(value = "Test Mapeamento para Salvar novo Usuario. Deve retornar Status(201) e Usuario Salvo.")
    // public void salvarUsuario() throws Exception {
        
    //     Usuario usuarioSalvo = getUsuario();
    //     UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioSalvo.getNome(), usuarioSalvo.getEmail(), usuarioSalvo.getSenha());
    //     Usuario usuarioConvertido = new Usuario(usuarioSalvo.getNome(), usuarioSalvo.getEmail(), usuarioSalvo.getSenha());

    //     Map<String, Object> map = new HashMap<>();
    //     map.put("email", "adn@gmail.com");
    //     map.put("senha", "123");

    //     String loginJSON = new ObjectMapper().writeValueAsString(map);
    //     String usuarioDTOJSON = new ObjectMapper().writeValueAsString(usuarioDTO);

    //     BDDMockito.given(implm.loadUserByUsername(usuarioSalvo.getEmail()))
    //             .willReturn(usuarioSalvo);

    //     MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
    //             .content(loginJSON))
    //             .andExpect(status().isOk()).andDo(print()).andReturn();

    //     String token = formatarToken(result.getResponse().getContentAsString()); 

    //     Mockito.doReturn(usuarioRepository).when(context)
    //                 .getBean(UsuarioRepository.class);
        
    //     BDDMockito.given(context.getBean(UsuarioRepository.class))
    //                 .willReturn(usuarioRepository);

    //     BDDMockito.given(modelMapper.map(usuarioDTO, Usuario.class)).willReturn(usuarioConvertido);

    //     modelMapper.validate();

    //     BDDMockito.given(modelMapper.map(usuarioSalvo, UsuarioResponseDTO.class))
    //                 .willReturn(getUsuarioDTO(usuarioSalvo));

    //     BDDMockito.given(usuarioService.salvar(Mockito.any(Usuario.class)))
    //                 .willReturn(usuarioSalvo);

    //     BDDMockito.given(usuarioRepository.findByEmail(usuarioSalvo.getEmail()))
    //                 .willReturn(Optional.of(usuarioSalvo));


    //     mockMvc.perform(MockMvcRequestBuilders
    //                                     .post(BASE_URL)
    //                                     .header("Authorization", token)
    //                                     .contentType(MediaType.APPLICATION_JSON)
    //                                     .content(usuarioDTOJSON))
    //             .andDo(print())

    //             .andExpect(status().isCreated())
    //             .andExpect(jsonPath("id").value(100l))
    //             .andExpect(jsonPath("email").value(usuarioSalvo.getEmail()))
    //             .andExpect(jsonPath("nome").value(usuarioSalvo.getNome()));

    // }

    @Test
    @DisplayName(value = "Test Deve retornar uma lista com todos os Usuarios e Status Ok(200).")
    public void obterTodosUsuarios() throws Exception {

        Usuario usuario = getUsuario();
        List<Usuario> usuarios = Arrays.asList(usuario);
        List<UsuarioResponseDTO> usuariosDTO = Arrays.asList(getUsuarioDTO(usuario));

        String usuariosJSON = new ObjectMapper().writeValueAsString(usuariosDTO);

        BDDMockito.given(usuarioService.obterTodosDTO())
                    .willReturn(usuarios);

        BDDMockito.given(modelMapper.map(usuario, UsuarioResponseDTO.class))
                    .willReturn(getUsuarioDTO(usuario));

        this.mockMvc.perform(get(BASE_URL))
                    .andDo(print())

                    .andExpect(status().isOk())
                    .andExpect(content().json(usuariosJSON));
    }

    @Test
    @DisplayName(value = "Test Mapeamento para obter Usuario por ID. Deve retornar Status(200) e Usuario buscado.")
    public void obterUsuarioPorId() throws Exception {
        Long id = 100l;
        Usuario usuario = getUsuario();

        // SIMULAÇÃO

        BDDMockito.given(usuarioService.obterPorId(id))
                    .willReturn(Optional.of(usuario));

        BDDMockito.given(modelMapper.map(usuario, UsuarioResponseDTO.class))
                    .willReturn(getUsuarioDTO(usuario));
        
       // AÇÃO

        this.mockMvc.perform(get(BASE_URL.concat("/" + id))
                             .accept(MediaType.APPLICATION_JSON))
                .andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(usuario.getId()))
                .andExpect(jsonPath("nome").value(usuario.getNome()))
                .andExpect(jsonPath("email").value(usuario.getEmail()));
    }

    @Test
    @DisplayName("Deve retornar Status(404) ao tentar obter um Usuario com ID inexistente.")
    public void obterUsuarioIDInexistente() throws Exception {
        Long id = 101l;

        this.mockMvc.perform(get(BASE_URL.concat("/" + id)))
                        .andDo(print())

                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test Obter Pedidos de Usuario com ID do Usuario." + 
                 " Deve retornar Status(200) e Lista de Pedidos do Usuario.")
    public void obterPedidosUsuario() throws Exception {
        Usuario usuario = getUsuario();
        Pedido pedido = getPedido();
        usuario.setPedidos(Arrays.asList(pedido));

        BDDMockito.given(usuarioService.obterPorId(usuario.getId()))
                    .willReturn(Optional.of(usuario));

        this.mockMvc.perform(get(BASE_URL.concat("/" + usuario.getId() + "/pedidos")))
                        .andDo(print())
                        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test Deve enviar e-mail e retornar Status(200)")
    public void enviarEmail() throws Exception {

        this.mockMvc.perform(get(BASE_URL.concat("/enviar-email")))
                        .andDo(print())
                        .andExpect(status().isOk());

    }

      private Pedido getPedido() {
          return new Pedido(28239382,
                            LocalDateTime.now(),
                            BigDecimal.valueOf(19.90),
                            null, null);
    }
    
    private Usuario getUsuario() {
        return new Usuario(100l, "Anderson Test", "adn@gmail.com",
                 new BCryptPasswordEncoder().encode("123"));
    }

    private UsuarioResponseDTO getUsuarioDTO(Usuario user) {
        return new UsuarioResponseDTO(user.getId(),
                                      user.getNome(),
                                      user.getEmail());
    }
}
