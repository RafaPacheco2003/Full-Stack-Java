package com.mini.trailers.mini_trailers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll() // Rutas públicas
                .requestMatchers("/admin/**").authenticated() // Rutas de /admin requieren autenticación
                .anyRequest().permitAll() // Otras rutas permitidas sin autenticación
            )
            .formLogin((form) -> form
                .loginPage("/login") // Página de login personalizada
                .permitAll()
            )
            .logout((logout) -> logout.permitAll())
            .csrf().disable(); // Deshabilita CSRF para simplificar, aunque no recomendado en producción.

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
