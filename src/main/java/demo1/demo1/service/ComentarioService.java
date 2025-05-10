package demo1.demo1.service;


import demo1.demo1.model.*;
import demo1.demo1.repository.ComentarioRepository;
import demo1.demo1.repository.TemaRepository;
import demo1.demo1.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class ComentarioService {
    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final TemaRepository temaRepository;

    public ComentarioService(
        ComentarioRepository comentarioRepository,
        UsuarioRepository usuarioRepository,
        TemaRepository temaRepository
    ) {
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.temaRepository = temaRepository;
    }

    @Transactional
    public Comentario crearComentarioDesdeFrontend(Long temaId, String email, String contenido) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Tema tema = temaRepository.findById(temaId)
            .orElseThrow(() -> new RuntimeException("Tema no encontrado"));

        Comentario comentario = new Comentario();
        comentario.setContenido(contenido);
        comentario.setUsuario(usuario);
        comentario.setTema(tema);

        return comentarioRepository.save(comentario);
    }


    @Transactional(readOnly = true)
    public List<Comentario> listarPorTema(Long temaId) {
        return comentarioRepository.findByTemaId(temaId);
    }

    @Transactional(readOnly = true)
    public List<Comentario> listarTodos() {
        return comentarioRepository.findAll();
    }


    @Transactional
    public void eliminarComentario(Long id) {
        Comentario comentario = comentarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
        comentarioRepository.delete(comentario);
    }

}
