package demo1.demo1.controller;

import demo1.config.NoSecurityConfig;
import demo1.config.TestSecurityConfig;
import demo1.demo1.model.Comentario;
import demo1.demo1.service.ComentarioService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



@WebMvcTest(ComentarioController.class)
@Import(TestSecurityConfig.class)
public class ComentarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComentarioService comentarioService;

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    void testCrearComentario() throws Exception {
        Comentario comentarioMock = new Comentario();
        comentarioMock.setContenido("Este es un comentario");

        when(comentarioService.crearComentarioDesdeFrontend(1L, "test@example.com", "Este es un comentario"))
            .thenReturn(comentarioMock);

        String requestBody = "{\"contenido\": \"Este es un comentario\"}";

        mockMvc.perform(post("/api/comentarios/tema/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andDo(print()) // Para ver detalles en consola
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.contenido").value("Este es un comentario"));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    void testListarPorTema() throws Exception {
        Comentario c1 = new Comentario();
        c1.setContenido("Comentario 1");

        Comentario c2 = new Comentario();
        c2.setContenido("Comentario 2");

        when(comentarioService.listarPorTema(1L)).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/api/comentarios/tema/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].contenido").value("Comentario 1"))
                .andExpect(jsonPath("$[1].contenido").value("Comentario 2"));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    void testListarTodos() throws Exception {
        Comentario c1 = new Comentario();
        c1.setContenido("Comentario A");

        when(comentarioService.listarTodos()).thenReturn(List.of(c1));

        mockMvc.perform(get("/api/comentarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void testEliminarComentario() throws Exception {
        doNothing().when(comentarioService).eliminarComentario(1L);

        mockMvc.perform(delete("/api/comentarios/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Comentario eliminado exitosamente"));
    }

}
