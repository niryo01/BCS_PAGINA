package com.example.demo.controller;

import com.example.demo.model.Cita;
import com.example.demo.repository.CitaRepository;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class CitaController {
    private final CitaRepository citaRepository;

    public CitaController(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    

    @GetMapping("/listaCitas")
    public String mostrarListaCitas(Model model) {
        List<Cita> citas = citaRepository.findAll();
        model.addAttribute("citas", citas);
        return "listaCitas";
    }

    @GetMapping("/citas/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cita", new Cita());
        return "formularioCitas";
    }

   @PostMapping("/citas/nueva")
public String agregarCita(@Valid @ModelAttribute("cita") Cita cita,
                          BindingResult bindingResult, Model model) {

    System.out.println("=== Método agregarCita INVOCADO ===");
    System.out.println("Datos recibidos: " + cita.getNombreCliente() + ", " + cita.getFecha() + ", " + cita.getHora() + ", " + cita.getDescripcion());

    LocalDate hoy = LocalDate.now();
    LocalTime ahora = LocalTime.now();

    if (cita.getFecha() != null && cita.getFecha().isBefore(hoy)) {
        bindingResult.rejectValue("fecha", "error.cita", "La fecha no puede estar en el pasado");
    }

    if (cita.getFecha() != null && cita.getFecha().isEqual(hoy) &&
        cita.getHora() != null && cita.getHora().isBefore(ahora)) {
        bindingResult.rejectValue("hora", "error.cita", "La hora no puede estar en el pasado");
    }

    if (bindingResult.hasErrors()) {
        return "formularioCitas";
    }

    citaRepository.save(cita);
    model.addAttribute("mensajeExito", "¡Cita registrada con éxito! Nos comunicaremos en breve.");

    model.addAttribute("cita", new Cita());

    return "formularioCitas"; // Volver a la misma vista pero con el mensaje
}


    @PostMapping("/citas/eliminar/{id}")
    public String eliminarCita(@PathVariable("id") int id) {
        citaRepository.deleteById(id);
        return "redirect:/listaCitas";
    }

    

}
