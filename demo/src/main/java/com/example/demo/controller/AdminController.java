package com.example.demo.controller;

import com.example.demo.model.Cita;
import com.example.demo.repository.CitaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private final CitaRepository citaRepository;

    public AdminController(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin"; 
    }

    @GetMapping("/admin/lista-citas")
    public String mostrarCitas(Model model) {
        List<Cita> citas = citaRepository.findAll();
        model.addAttribute("citas", citas);
        return "listaCitas"; 
    }
}
