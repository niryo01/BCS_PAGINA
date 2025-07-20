package com.example.demo.dao;

import java.util.List;

public interface GenericDAO<T> {
    List<T> obtenerTodos();
    void agregar(T t);
    void eliminar(T t);
    void actualizar(T t); // Opcional, si lo necesitas
    T obtenerPorId(int id); // Opcional, si tus modelos tienen ID
}
