package demo1.demo1.service;

import demo1.demo1.dto.UsuarioUpdateDTO;
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


    public UsuarioService(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder 
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }


    public Usuario buscarPorEmail(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        return usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

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

    @Transactional
    public Usuario actualizarPorEmail(String email, UsuarioUpdateDTO dto) {
      Usuario usuario = usuarioRepository.findByEmail(email)
          .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
  
      if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
        usuario.setNombre(dto.getNombre());
      }
      if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
      }
  
      return usuarioRepository.save(usuario);
    }
    

    


}