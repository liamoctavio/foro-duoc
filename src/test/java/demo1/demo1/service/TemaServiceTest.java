package demo1.demo1.service;

import demo1.demo1.dto.TemaDTO;
import demo1.demo1.model.Categoria;
import demo1.demo1.model.Tema;
import demo1.demo1.model.Usuario;
import demo1.demo1.repository.CategoriaRepository;
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
public class TemaServiceTest {

    @Mock
    private TemaRepository temaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private TemaService temaService;

    private Usuario usuario;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("usuario@example.com");

        categoria = new Categoria();
        categoria.setId(1L);
    }

    @Test
    void testCrearTema() {
        TemaDTO temaDTO = new TemaDTO();
        temaDTO.setUsuarioId(1L);
        temaDTO.setCategoriaId(1L);
        temaDTO.setTitulo("Título de prueba");
        temaDTO.setContenido("Contenido de prueba");

        Tema temaGuardado = new Tema();
        temaGuardado.setTitulo(temaDTO.getTitulo());
        temaGuardado.setContenido(temaDTO.getContenido());
        temaGuardado.setUsuario(usuario);
        temaGuardado.setCategoria(categoria);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(temaRepository.save(any(Tema.class))).thenReturn(temaGuardado);

        Tema resultado = temaService.crearTema(temaDTO);

        assertNotNull(resultado);
        assertEquals("Título de prueba", resultado.getTitulo());
        assertEquals("Contenido de prueba", resultado.getContenido());
        assertEquals(usuario, resultado.getUsuario());
        assertEquals(categoria, resultado.getCategoria());

        verify(usuarioRepository).findById(1L);
        verify(categoriaRepository).findById(1L);
        verify(temaRepository).save(any(Tema.class));
    }

    @Test
    void testListarPorCategoria() {
        Long categoriaId = 1L;
        List<Tema> temas = List.of(new Tema(), new Tema());

        when(temaRepository.findByCategoriaId(categoriaId)).thenReturn(temas);

        List<Tema> resultado = temaService.listarPorCategoria(categoriaId);

        assertEquals(2, resultado.size());
        verify(temaRepository).findByCategoriaId(categoriaId);
    }

    @Test
    void testEliminarTema() {
        Long temaId = 1L;
        Tema tema = new Tema();
        when(temaRepository.findById(temaId)).thenReturn(Optional.of(tema));

        temaService.eliminarTema(temaId);

        verify(temaRepository).findById(temaId);
        verify(temaRepository).delete(tema);
    }

    @Test
    void testListarTodos() {
        List<Tema> temas = List.of(new Tema(), new Tema());
        when(temaRepository.findAll()).thenReturn(temas);

        List<Tema> resultado = temaService.listarTodos();

        assertEquals(2, resultado.size());
        verify(temaRepository).findAll();
    }



}
