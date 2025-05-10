package demo1.config;


import demo1.demo1.config.WebSecurityConfig;
import demo1.demo1.controller.TemaController;
import demo1.demo1.model.TextPasswordEncoder;
import demo1.demo1.service.CustomUserDetailsService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

public class WebSecurityConfigTest {

    @Test
    void testPasswordEncoderEsTextoPlano() {
        WebSecurityConfig config = new WebSecurityConfig(null);
        PasswordEncoder encoder = config.passwordEncoder();

        assertNotNull(encoder);
        assertInstanceOf(TextPasswordEncoder.class, encoder);

        String raw = "clave123";
        String encoded = encoder.encode(raw);

        assertEquals(raw, encoded); // TextPasswordEncoder no encripta
    }


}
