package demo1.demo1.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import demo1.demo1.model.TextPasswordEncoder;
import demo1.demo1.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/categorias/**").permitAll()
            // hilos POR CATEGORÍA → público (para que cualquiera, incluso sin loguearse, vea los temas de esa categoría)
            .requestMatchers(HttpMethod.GET, "/api/temas/categoria/**").permitAll()
              
            // crear hilo → cualquier usuario autenticado
            .requestMatchers(HttpMethod.POST, "/api/temas/crear").authenticated()
            
            // LISTADO GLOBAL (/api/temas) y ELIMINAR → sólo ADMIN
            .requestMatchers(HttpMethod.GET,    "/api/temas").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/temas/**").hasRole("ADMIN")
            
            // comentarios: listar público, crear autenticado, borrar ADMIN
            .requestMatchers(HttpMethod.GET,    "/api/comentarios/tema/**").permitAll()
            .requestMatchers(HttpMethod.POST,   "/api/comentarios/tema/**").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/api/comentarios/**").hasRole("ADMIN")
            
            // edición de perfil → cualquier autenticado
            .requestMatchers(HttpMethod.PUT,    "/api/usuarios/me").authenticated()
            .anyRequest().permitAll()
            )
            .userDetailsService(userDetailsService) 
            .httpBasic();
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new TextPasswordEncoder(); 
    }

   
}