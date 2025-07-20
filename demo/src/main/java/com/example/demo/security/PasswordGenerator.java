package com.example.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        //ESTO SOLO FUE DE USO TEMPORAL PARA REEMPLAZAR LAS CONTRASEÑAS
        //CON LOS USUARIOS QUE YA EXISTEN
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin123"));
        System.out.println(encoder.encode("usuario123"));
        System.out.println(encoder.encode("12345")); 
    }
}