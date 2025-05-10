package demo1.demo1.controller;

import demo1.config.NoSecurityConfig;
import demo1.demo1.model.Categoria;
import demo1.demo1.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Import(NoSecurityConfig.class)
@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaRepository categoriaRepository;

    @Test
    void testPing() throws Exception {
        mockMvc.perform(get("/api/categorias/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("pong"));
    }

    @Test
    void testListarCategorias() throws Exception {
        Categoria cat1 = new Categoria();
        cat1.setId(1L);
        cat1.setNombre("Frontend");

        Categoria cat2 = new Categoria();
        cat2.setId(2L);
        cat2.setNombre("Backend");

        when(categoriaRepository.findAll()).thenReturn(List.of(cat1, cat2));

        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Frontend"))
                .andExpect(jsonPath("$[1].nombre").value("Backend"));
    }


}
