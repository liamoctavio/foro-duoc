package demo1.demo1.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_seq")
    @SequenceGenerator(
        name = "usuarios_seq",
        sequenceName = "usuarios_seq", // Nombre de la secuencia en Oracle
        allocationSize = 1 // Debe coincidir con INCREMENT BY
    )
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 10)
    private String rol; // 'USER' o 'ADMIN'

    // Getters y Setters (genera con Lombok o manualmente)
}
