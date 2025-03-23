package demo1.demo1.controller;

import demo1.demo1.model.*;
import demo1.demo1.service.ComentarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {
    private final ComentarioService comentarioService;

    @Autowired
    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping("/crear")
    public Comentario crearComentario(@RequestBody Comentario comentario) {
        return comentarioService.crearComentario(comentario);
    }

    @GetMapping("/tema/{temaId}")
    public List<Comentario> listarPorTema(@PathVariable Long temaId) {
        return comentarioService.listarPorTema(temaId);
    }

    @GetMapping
    public List<Comentario> listarTodos() {
        return comentarioService.listarTodos();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminarComentario(@PathVariable Long id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.ok("Comentario eliminado exitosamente");
    }
}
