package demo1.demo1.model;


import org.springframework.security.crypto.password.PasswordEncoder;

public class TextPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString(); // Devuelve la contraseña sin encriptar
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword); // Compara texto plano
    }
}