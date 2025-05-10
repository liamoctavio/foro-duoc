package demo1.demo1.controller;

import demo1.demo1.dto.TemaDTO;
import demo1.demo1.model.Tema;
import demo1.demo1.service.TemaService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import demo1.config.TestSecurityConfig;


@WebMvcTest(TemaController.class)
@Import(TestSecurityConfig.class)
public class TemaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TemaService temaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user@example.com")
    void testListarPorCategoria() throws Exception {
        Tema tema1 = new Tema();
        tema1.setTitulo("Tema 1");

        Tema tema2 = new Tema();
        tema2.setTitulo("Tema 2");

        when(temaService.listarPorCategoria(1L)).thenReturn(List.of(tema1, tema2));

        mockMvc.perform(get("/api/temas/categoria/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].titulo").value("Tema 1"))
                .andExpect(jsonPath("$[1].titulo").value("Tema 2"));
    }

    @Test
    @WithMockUser(username = "user@example.com")
    void testCrearTema() throws Exception {
        TemaDTO dto = new TemaDTO();
        dto.setTitulo("Nuevo tema");
        dto.setContenido("Contenido nuevo");
        dto.setUsuarioId(1L);
        dto.setCategoriaId(2L);

        Tema temaCreado = new Tema();
        temaCreado.setTitulo(dto.getTitulo());
        temaCreado.setContenido(dto.getContenido());

        when(temaService.crearTema(any(TemaDTO.class))).thenReturn(temaCreado);

        mockMvc.perform(post("/api/temas/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Nuevo tema"))
                .andExpect(jsonPath("$.contenido").value("Contenido nuevo"));
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void testListarTodos() throws Exception {
        Tema tema1 = new Tema();
        tema1.setTitulo("Tema A");

        when(temaService.listarTodos()).thenReturn(List.of(tema1));

        mockMvc.perform(get("/api/temas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void testEliminarTema() throws Exception {
        doNothing().when(temaService).eliminarTema(1L);

        mockMvc.perform(delete("/api/temas/1"))
                .andExpect(status().isNoContent());
    }


}
