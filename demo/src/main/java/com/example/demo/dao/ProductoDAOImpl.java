package com.example.demo.dao;

import com.example.demo.model.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ProductoDAOImpl implements ProductoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Producto> obtenerTodos() {
        String jpql = "SELECT p FROM Producto p";
        return entityManager.createQuery(jpql, Producto.class).getResultList();
    }

    @Override
    public Producto obtenerPorNombre(String nombre) {
        String jpql = "SELECT p FROM Producto p WHERE p.nombre = :nombre";
        List<Producto> resultados = entityManager.createQuery(jpql, Producto.class)
                .setParameter("nombre", nombre)
                .getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public void agregarProducto(Producto producto) {
        entityManager.persist(producto);
    }

    @Override
    public void actualizarProducto(Producto producto) {
        entityManager.merge(producto);
    }

    @Override
    public void eliminarProducto(String nombre) {
        Producto producto = obtenerPorNombre(nombre);
        if (producto != null) {
            entityManager.remove(producto);
        }
    }
}