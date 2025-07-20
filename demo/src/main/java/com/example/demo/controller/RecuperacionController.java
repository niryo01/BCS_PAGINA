package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtUtil;

@Controller
public class RecuperacionController {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mostrar formulario para recuperar contraseña
    @GetMapping("/recuperar")
    public String mostrarFormularioRecuperacion() {
        return "recuperacionContraseña"; // Tu vista existente
    }

    // Procesar el formulario: generar token y redirigir
    @PostMapping("/recuperar")
public String procesarRecuperacion(@RequestParam("username") String username, Model model) {
    Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);

    if (optionalUsuario.isEmpty()) {
        model.addAttribute("error", "Usuario no encontrado");
        return "recuperacionContraseña";
    }

    Usuario usuario = optionalUsuario.get(); // ✅ Ahora sí lo tienes correctamente

    String token = jwtUtil.generarTokenTemporal(usuario.getUsername());

    model.addAttribute("token", token);
    return "mostrarToken";
}

    // Mostrar formulario para escribir nueva contraseña
    @GetMapping("/reset-password")
    public String mostrarFormularioReset(@RequestParam("token") String token, Model model) {
        if (!jwtUtil.validarToken(token)) {
            model.addAttribute("error", "Token inválido o expirado");
            return "recuperacionContraseña";
        }

        model.addAttribute("token", token);
        return "resetPassword"; // Vista donde se cambia la contraseña
    }

    // Procesar nueva contraseña
    @PostMapping("/reset-password")
public String procesarResetPassword(
        @RequestParam("token") String token,
        @RequestParam("nuevaPassword") String nuevaPassword,
        Model model) {

    if (!jwtUtil.validarToken(token)) {
        model.addAttribute("error", "Token inválido o expirado");
        return "recuperacionContraseña";
    }

    String username = jwtUtil.obtenerUsernameDesdeToken(token);
    Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);

    if (optionalUsuario.isEmpty()) {
        model.addAttribute("error", "Usuario no encontrado");
        return "recuperacionContraseña";
    }

    Usuario usuario = optionalUsuario.get();
    usuario.setPassword(passwordEncoder.encode(nuevaPassword));
    usuarioRepository.save(usuario);

    model.addAttribute("mensaje", "Contraseña actualizada exitosamente");
    return "login";
}
}

