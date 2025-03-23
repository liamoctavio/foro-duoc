package demo1.demo1.repository;



import demo1.demo1.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TemaRepository extends JpaRepository<Tema, Long> {
    // Buscar temas por categoría
    List<Tema> findByCategoriaId(Long categoriaId);
}