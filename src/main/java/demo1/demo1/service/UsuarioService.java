package demo1.demo1.service;

import demo1.demo1.model.Usuario;
import demo1.demo1.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

import java.util.List;

import java.util.Optional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // public UsuarioService(UsuarioRepository usuarioRepository) {
    //     this.usuarioRepository = usuarioRepository;
    // }
    public UsuarioService(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder // Añadir como parámetro
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // @Transactional
    // public Usuario crearUsuario(Usuario usuario) {
    //     return usuarioRepository.save(usuario);
    // }
    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    // public Usuario buscarPorEmail(String email) {
    //     return usuarioRepository.findByEmail(email);
    // }
    public Usuario buscarPorEmail(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        return usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        // Buscar el usuario existente
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Actualizar campos permitidos
        if (usuarioActualizado.getNombre() != null) {
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
        }

        if (usuarioActualizado.getEmail() != null) {
            usuarioExistente.setEmail(usuarioActualizado.getEmail());
        }

        if (usuarioActualizado.getPassword() != null) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
        }

        if (usuarioActualizado.getRol() != null) {
            usuarioExistente.setRol(usuarioActualizado.getRol());
        }

        return usuarioRepository.save(usuarioExistente);
    }


}