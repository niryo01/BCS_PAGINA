package com.example.demo.controller;

import com.example.demo.model.RegistroDTO;
import com.example.demo.model.Usuario;
import com.example.demo.model.Rol;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.RolRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.Set;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new RegistroDTO());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") RegistroDTO dto,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            return "registro";
        }

        // Verificar si el nombre de usuario ya existe
        Optional<Usuario> existente = usuarioRepository.findByUsername(dto.getUsername());
        if (existente.isPresent()) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            return "registro";
        }

        // Verificar si las contraseñas coinciden
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "registro";
        }

        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(dto.getUsername());
        nuevoUsuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Asignar rol ROLE_USER
        Rol rolUser = rolRepository.findByNombre("ROLE_USER");
        nuevoUsuario.setRoles(Set.of(rolUser));

        // Guardar usuario
        usuarioRepository.save(nuevoUsuario);

        return "redirect:/login?registroExitoso";
    }
}
