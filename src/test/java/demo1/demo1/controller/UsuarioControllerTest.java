package demo1.demo1.controller;
import demo1.config.TestSecurityConfig;

import demo1.demo1.dto.UsuarioUpdateDTO;
import demo1.demo1.model.Usuario;
import demo1.demo1.service.UsuarioService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UsuarioController.class)
@Import(TestSecurityConfig.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegistroUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("nuevo@ejemplo.com");
        usuario.setPassword("1234");

        when(usuarioService.crearUsuario(any())).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("nuevo@ejemplo.com"));
    }

    @Test
    void testListarUsuarios() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("a@a.com");

        when(usuarioService.listarTodos()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testBuscarPorEmail() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("b@b.com");

        when(usuarioService.buscarPorEmail("b@b.com")).thenReturn(usuario);

        mockMvc.perform(get("/api/usuarios/buscar?email=b@b.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("b@b.com"));
    }

    @Test
    void testActualizarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("actualizado@ejemplo.com");

        when(usuarioService.actualizarUsuario(eq(1L), any())).thenReturn(usuario);

        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("actualizado@ejemplo.com"));
    }

    @Test
    void testLoginCorrecto() throws Exception {
        Usuario login = new Usuario();
        login.setEmail("login@ok.com");
        login.setPassword("123");

        Usuario dbUsuario = new Usuario();
        dbUsuario.setEmail("login@ok.com");
        dbUsuario.setPassword("123");

        when(usuarioService.buscarPorEmail("login@ok.com")).thenReturn(dbUsuario);

        mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("login@ok.com"));
    }

    @Test
    void testLoginContrasenaIncorrecta() throws Exception {
        Usuario login = new Usuario();
        login.setEmail("login@fail.com");
        login.setPassword("123");

        Usuario dbUsuario = new Usuario();
        dbUsuario.setEmail("login@fail.com");
        dbUsuario.setPassword("otro");

        when(usuarioService.buscarPorEmail("login@fail.com")).thenReturn(dbUsuario);

        mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "yo@ejemplo.com")
    void testObtenerUsuarioAutenticado() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("yo@ejemplo.com");

        when(usuarioService.buscarPorEmail("yo@ejemplo.com")).thenReturn(usuario);

        mockMvc.perform(get("/api/usuarios/me"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("yo@ejemplo.com"));
    }

    @Test
    @WithMockUser(username = "yo@ejemplo.com")
    void testActualizarMiPerfil() throws Exception {
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO();
        dto.setNombre("Nuevo Nombre");
        dto.setPassword("nueva");

        Usuario usuario = new Usuario();
        usuario.setEmail("yo@ejemplo.com");
        usuario.setNombre("Nuevo Nombre");

        when(usuarioService.actualizarPorEmail(eq("yo@ejemplo.com"), any(UsuarioUpdateDTO.class)))
            .thenReturn(usuario);

        mockMvc.perform(put("/api/usuarios/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Nuevo Nombre"));
    }

    @Test
    void testBuscarUsuarioNoExistenteLanza400() throws Exception {
        when(usuarioService.buscarPorEmail("404@no.com"))
            .thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(get("/api/usuarios/buscar?email=404@no.com"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Error al registrar el usuario: Usuario no encontrado"));
    }

}
