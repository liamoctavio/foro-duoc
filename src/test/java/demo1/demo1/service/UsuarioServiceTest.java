package demo1.demo1.service;

import demo1.demo1.dto.UsuarioUpdateDTO;
import demo1.demo1.model.Usuario;
import demo1.demo1.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@example.com");
        usuario.setPassword("1234");
        usuario.setNombre("Test");
        usuario.setRol("USER");
    }

    @Test
    void testCrearUsuario() {
        when(passwordEncoder.encode("1234")).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.crearUsuario(usuario);

        assertNotNull(resultado);
        verify(passwordEncoder).encode("1234");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testBuscarPorEmail_Existe() {
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorEmail("test@example.com");

        assertEquals("test@example.com", resultado.getEmail());
    }

    @Test
    void testBuscarPorEmail_NoExiste() {
        when(usuarioRepository.findByEmail("desconocido@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            usuarioService.buscarPorEmail("desconocido@example.com");
        });
    }

    @Test
    void testListarTodos() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario, new Usuario()));

        List<Usuario> resultado = usuarioService.listarTodos();

        assertEquals(2, resultado.size());
    }

    @Test
    void testActualizarUsuario() {
        Usuario actualizacion = new Usuario();
        actualizacion.setNombre("NuevoNombre");
        actualizacion.setEmail("nuevo@example.com");
        actualizacion.setPassword("nuevaPass");
        actualizacion.setRol("ADMIN");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("nuevaPass")).thenReturn("encodedNewPass");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.actualizarUsuario(1L, actualizacion);

        assertEquals("NuevoNombre", resultado.getNombre());
        assertEquals("nuevo@example.com", resultado.getEmail());
        assertEquals("encodedNewPass", resultado.getPassword());
        assertEquals("ADMIN", resultado.getRol());
    }

    @Test
    void testActualizarPorEmail() {
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO();
        dto.setNombre("NuevoNombreDTO");
        dto.setPassword("nuevaDTO");

        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("nuevaDTO")).thenReturn("encodedDTO");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.actualizarPorEmail("test@example.com", dto);

        assertEquals("NuevoNombreDTO", resultado.getNombre());
        assertEquals("encodedDTO", resultado.getPassword());
    }


}
