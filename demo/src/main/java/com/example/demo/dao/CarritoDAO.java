package com.example.demo.dao;

import com.example.demo.model.CarritoItem;
import java.util.List;

public interface CarritoDAO {
    List<CarritoItem> obtenerTodos();
    CarritoItem obtenerPorProductoId(int idProducto); 
    void agregarItem(CarritoItem item);
    void actualizarItem(CarritoItem item);
    void eliminarItemPorProductoId(int idProducto);
    void vaciarCarrito();
}