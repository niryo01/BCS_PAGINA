package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "MARCOSWEB";
    private final long EXPIRATION_TIME = 15 * 60 * 1000; // 15 minutos

    // Método para generar token con username y rol como Strings
    public String generarToken(String username, String rol) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rol); 
        return generarToken(username, claims); 
    }

    // Método general con claims personalizados
    public String generarToken(String username, Map<String, Object> claims) {
        Map<String, Object> claimsMutable = new HashMap<>(claims);
        return Jwts.builder()
                .setClaims(claimsMutable)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    //  Método para generar token temporal sin claims 
    public String generarTokenTemporal(String username) {
        return generarToken(username, Map.of());
    }

    //  Valida el token
    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    //  Extrae el username
    public String obtenerUsernameDesdeToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Extrae un claim específico 
    public Object obtenerClaim(String token, String claimKey) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.get(claimKey);
    }
}
