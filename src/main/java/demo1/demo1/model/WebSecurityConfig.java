package demo1.demo1.model;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import demo1.demo1.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
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
                .requestMatchers("/api/comentarios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN") 
                .anyRequest().permitAll()
            )
            .userDetailsService(userDetailsService) 
            .httpBasic();
        return http.build();
    }



    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf().disable()
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers("/api/comentarios/**").hasRole("ADMIN") // Solo ADMIN accede a endpoints de comentarios
    //             .anyRequest().permitAll()
    //         )
    //         .httpBasic();
    //     return http.build();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new TextPasswordEncoder(); // Usa el encoder personalizado
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(csrf -> csrf.disable()) // Desactivamos CSRF porque estás usando solo backend y Postman
    //         .authorizeHttpRequests(authorize -> authorize
    //             .requestMatchers("/", "/home").permitAll()
    //             .anyRequest().authenticated()
    //         )
    //         .httpBasic()
    //         .and() // Esto habilita autenticación básica (perfecta para Postman)
    //         .logout(logout -> logout.permitAll());
    
    //     return http.build();
    // }


    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user = User.builder()
    //         .username("user")
    //         .password(passwordEncoder().encode("password"))
    //         .roles("USER")
    //         .build();

    //     UserDetails admin = User.builder()
    //     .username("admin")
    //     .password(passwordEncoder().encode("adminpassword"))
    //     .roles("ADMIN")
    //     .build();

    //     return new InMemoryUserDetailsManager(user, admin);
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }


   
}