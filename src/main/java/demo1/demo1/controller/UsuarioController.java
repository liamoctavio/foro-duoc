package demo1.demo1.controller;

import demo1.demo1.dto.UsuarioUpdateDTO;
import demo1.demo1.model.Usuario;
import demo1.demo1.service.UsuarioService;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/buscar")
    public Usuario buscarPorEmail(@RequestParam String email) {
        return usuarioService.buscarPorEmail(email);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
        @PathVariable Long id,
        @RequestBody Usuario usuarioActualizado
    ) {
        Usuario usuario = usuarioService.actualizarUsuario(id, usuarioActualizado);
        return ResponseEntity.ok(usuario);
    }

    //Metodo para que se conecten con el front
    
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario loginData) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(loginData.getEmail());

            if (!usuario.getPassword().equals(loginData.getPassword())) {
                return ResponseEntity.status(401).body(null); // Contraseña incorrecta
            }

            return ResponseEntity.ok(usuario); // Login correcto
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(401).body(null); // Email no encontrado
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> obtenerUsuarioAutenticado(Authentication auth) {
        Usuario usuario = usuarioService.buscarPorEmail(auth.getName());
        return ResponseEntity.ok(usuario);
    }


    // @PutMapping("/me")
    // public ResponseEntity<Usuario> actualizarMiPerfil(
    //     @RequestBody Usuario usuarioActualizado,
    //     Authentication authentication
    // ) {
    //     String email = authentication.getName();
    //     Usuario actualizado = usuarioService.actualizarPorEmail(email, usuarioActualizado);
    //     return ResponseEntity.ok(actualizado);
    // }

    @PutMapping("/me")
    public ResponseEntity<Usuario> actualizarMiPerfil(
        @RequestBody UsuarioUpdateDTO dto,
        Authentication auth
    ) {
        String email = auth.getName();
        Usuario actualizado = usuarioService.actualizarPorEmail(email, dto);
        return ResponseEntity.ok(actualizado);
    }






}
