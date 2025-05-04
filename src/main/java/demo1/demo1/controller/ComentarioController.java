package demo1.demo1.controller;

import demo1.demo1.model.*;
import demo1.demo1.service.ComentarioService;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {
    private final ComentarioService comentarioService;

    @Autowired
    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    // @PostMapping("/crear")
    // public Comentario crearComentario(@RequestBody Comentario comentario) {
    //     return comentarioService.crearComentario(comentario);
    // }
    @PostMapping("/tema/{temaId}")
    public ResponseEntity<Comentario> crearComentario(
            @PathVariable Long temaId,
            @RequestBody Map<String, String> cuerpo,
            Authentication auth) {

        String contenido = cuerpo.get("contenido");
        String email = auth.getName(); 

        Comentario nuevoComentario = comentarioService.crearComentarioDesdeFrontend(temaId, email, contenido);
        return ResponseEntity.ok(nuevoComentario);
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
