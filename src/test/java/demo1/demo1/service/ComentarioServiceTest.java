package demo1.demo1.service;

import demo1.demo1.model.Comentario;
import demo1.demo1.model.Tema;
import demo1.demo1.model.Usuario;
import demo1.demo1.repository.ComentarioRepository;
import demo1.demo1.repository.TemaRepository;
import demo1.demo1.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComentarioServiceTest {

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TemaRepository temaRepository;

    @InjectMocks
    private ComentarioService comentarioService;

    private Usuario usuario;
    private Tema tema;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setEmail("usuario@example.com");

        tema = new Tema();
        tema.setId(1L);
    }

    @Test
    void testCrearComentarioDesdeFrontend() {
        
        String contenido = "Este es un comentario";
        when(usuarioRepository.findByEmail("usuario@example.com")).thenReturn(Optional.of(usuario));
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema));

        Comentario comentarioGuardado = new Comentario();
        comentarioGuardado.setContenido(contenido);
        comentarioGuardado.setUsuario(usuario);
        comentarioGuardado.setTema(tema);

        when(comentarioRepository.save(any(Comentario.class))).thenReturn(comentarioGuardado);

        
        Comentario resultado = comentarioService.crearComentarioDesdeFrontend(1L, "usuario@example.com", contenido);

        
        assertNotNull(resultado);
        assertEquals(contenido, resultado.getContenido());
        assertEquals(usuario, resultado.getUsuario());
        assertEquals(tema, resultado.getTema());

        verify(usuarioRepository).findByEmail("usuario@example.com");
        verify(temaRepository).findById(1L);
        verify(comentarioRepository).save(any(Comentario.class));
    }

    @Test
    void testListarPorTema() {
        Long temaId = 1L;
        List<Comentario> comentarios = List.of(new Comentario(), new Comentario());

        when(comentarioRepository.findByTemaId(temaId)).thenReturn(comentarios);

        List<Comentario> resultado = comentarioService.listarPorTema(temaId);

        assertEquals(2, resultado.size());
        verify(comentarioRepository, times(1)).findByTemaId(temaId);
    }

    @Test
    void testEliminarComentario() {
        Long comentarioId = 1L;
        Comentario comentario = new Comentario();

        when(comentarioRepository.findById(comentarioId)).thenReturn(Optional.of(comentario));

        comentarioService.eliminarComentario(comentarioId);

        verify(comentarioRepository).findById(comentarioId);
        verify(comentarioRepository).delete(comentario);
    }

    
}
