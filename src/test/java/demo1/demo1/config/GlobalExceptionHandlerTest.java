package demo1.demo1.config;
import demo1.demo1.config.GlobalExceptionHandler;
import demo1.demo1.controller.UsuarioController;
import demo1.demo1.service.UsuarioService;
import demo1.demo1.Demo1Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UsuarioController.class)
@Import(GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    @WithMockUser(username = "test@example.com")
    void testManejoDeExcepcionGlobal() throws Exception {
        when(usuarioService.buscarPorEmail("noexiste@correo.com"))
            .thenThrow(new RuntimeException("No encontrado"));

        mockMvc.perform(get("/api/usuarios/buscar?email=noexiste@correo.com"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Error al registrar el usuario: No encontrado"));
    }

}
