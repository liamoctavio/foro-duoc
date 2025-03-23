package demo1.demo1.repository;

import demo1.demo1.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    // Busca comentarios por ID de tema
    List<Comentario> findByTemaId(Long temaId);
}
