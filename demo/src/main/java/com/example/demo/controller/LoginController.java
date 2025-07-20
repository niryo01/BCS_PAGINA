package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html en templates
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado"; // acceso-denegado.html en templates
    }

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/login"; // Redirige raíz a login
    }

    
}