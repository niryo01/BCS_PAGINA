package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login","/acceso-denegado", 
    "/recuperar", 
    "/reset-password",  "/registro", "/styles.css", "/css/**", "/js/**", "/images/**").permitAll()
  // permite login y recursos
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/index").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()  // requiere login para todo lo demás
            )
            .formLogin(login -> login
                .loginPage("/login")               // tu login personalizado
                .successHandler(loginSuccessHandler) // redirige según el rol
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")  // a dónde ir al cerrar sesión
                .permitAll()
            );

        return http.build();
    }

    @Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}