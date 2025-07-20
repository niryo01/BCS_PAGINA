package com.example.demo.controller;

import com.example.demo.model.CarritoItem;
import com.example.demo.model.Producto;
import com.example.demo.repository.CarritoItemRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final ProductoRepository productoRepository;
    private final CarritoItemRepository carritoItemRepository;

    public CarritoController(ProductoRepository productoRepository, CarritoItemRepository carritoItemRepository) {
        this.productoRepository = productoRepository;
        this.carritoItemRepository = carritoItemRepository;
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam String nombre,
                                   @RequestParam int cantidad) {
        if (cantidad < 1) cantidad = 1;
        if (cantidad > 20) cantidad = 20;

        Producto producto = productoRepository.findByNombre(nombre).orElse(null);
        if (producto == null) {
            return "redirect:/tienda";
        }

        CarritoItem itemExistente = carritoItemRepository.findByProducto_IdProducto(producto.getIdProducto()).orElse(null);

        if (itemExistente != null) {
            int nuevaCantidad = itemExistente.getCantidad() + cantidad;
            if (nuevaCantidad > 20) nuevaCantidad = 20;
            itemExistente.setCantidad(nuevaCantidad);
            itemExistente.setPrecioTotal(itemExistente.getPrecioUnitario() * nuevaCantidad);
            carritoItemRepository.save(itemExistente);
        } else {
            CarritoItem nuevoItem = new CarritoItem(producto, cantidad);
            nuevoItem.setPrecioTotal(producto.getPrecio() * cantidad);
            carritoItemRepository.save(nuevoItem);
        }

        return "redirect:/carrito";
    }

    @GetMapping
    public String mostrarCarrito(Model model) {
        List<CarritoItem> carrito = carritoItemRepository.findAll();

        if (carrito.isEmpty()) {
            model.addAttribute("mensaje", "El carrito está vacío.");
        } else {
            double total = carrito.stream()
                .mapToDouble(CarritoItem::getPrecioTotal)
                .sum();
            model.addAttribute("total", total);
            model.addAttribute("productos", carrito);
        }

        return "carrito";
    }

    @GetMapping("/eliminar")
    public String eliminarProducto(@RequestParam String nombre) {
        Producto producto = productoRepository.findByNombre(nombre).orElse(null);
        if (producto != null) {
            carritoItemRepository.findByProducto_IdProducto(producto.getIdProducto())
                .ifPresent(carritoItemRepository::delete);
        }
        return "redirect:/carrito";
    }

    @GetMapping("/vaciar")
    public String vaciarCarrito() {
        carritoItemRepository.deleteAll();
        return "redirect:/carrito";
    }
}
