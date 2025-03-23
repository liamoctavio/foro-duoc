package demo1.demo1.repository;

import demo1.demo1.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para buscar por email (útil para login)
    // Usuario findByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}