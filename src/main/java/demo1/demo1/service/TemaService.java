package demo1.demo1.service;

import demo1.demo1.dto.TemaDTO;
import demo1.demo1.model.Categoria;
import demo1.demo1.model.Tema;
import demo1.demo1.model.Usuario;
import demo1.demo1.repository.TemaRepository;
import demo1.demo1.repository.UsuarioRepository;
import demo1.demo1.repository.CategoriaRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TemaService {
    private final TemaRepository temaRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    // Constructor actualizado
    public TemaService(
        TemaRepository temaRepository,
        UsuarioRepository usuarioRepository, // <-- Incluir este parámetro
        CategoriaRepository categoriaRepository
    ) {
        this.temaRepository = temaRepository;
        this.usuarioRepository = usuarioRepository; // <-- Inicializar
        this.categoriaRepository = categoriaRepository;
    }


    public Tema crearTema(TemaDTO temaDTO) {
        Usuario usuario = usuarioRepository.findById(temaDTO.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Categoria categoria = categoriaRepository.findById(temaDTO.getCategoriaId())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Tema tema = new Tema();
        tema.setTitulo(temaDTO.getTitulo());
        tema.setContenido(temaDTO.getContenido());
        tema.setUsuario(usuario);
        tema.setCategoria(categoria);

        return temaRepository.save(tema);
    }

    public List<Tema> listarPorCategoria(Long categoriaId) {
        return temaRepository.findByCategoriaId(categoriaId); 
    }
}