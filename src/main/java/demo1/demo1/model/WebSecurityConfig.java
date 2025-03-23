package demo1.demo1.model;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new TextPasswordEncoder(); 
    }

   
}