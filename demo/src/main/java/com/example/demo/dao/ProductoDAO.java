package com.example.demo.dao;

import com.example.demo.model.Producto;
import java.util.List;

public interface ProductoDAO {
    List<Producto> obtenerTodos();
    Producto obtenerPorNombre(String nombre);
    void agregarProducto(Producto producto);
    void actualizarProducto(Producto producto);
    void eliminarProducto(String nombre);
}
