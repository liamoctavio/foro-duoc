package demo1.demo1.service;

import demo1.demo1.dto.TemaDTO;
import demo1.demo1.model.Categoria;
import demo1.demo1.model.Tema;
import demo1.demo1.model.Usuario;
import demo1.demo1.repository.TemaRepository;
import demo1.demo1.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
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
        UsuarioRepository usuarioRepository, 
        CategoriaRepository categoriaRepository
    ) {
        this.temaRepository = temaRepository;
        this.usuarioRepository = usuarioRepository; 
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

    @Transactional
    public void eliminarTema(Long id) {
        Tema tema = temaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tema no encontrado con id: " + id));
        temaRepository.delete(tema);
    }

    @Transactional(readOnly = true)
    public List<Tema> listarTodos() {
        return temaRepository.findAll();
    }
}