package com.example.demo.dao;

import com.example.demo.model.CarritoItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CarritoDAOImpl implements CarritoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CarritoItem> obtenerTodos() {
        String jpql = "SELECT c FROM CarritoItem c";
        return entityManager.createQuery(jpql, CarritoItem.class).getResultList();
    }

    @Override
    public CarritoItem obtenerPorProductoId(int idProducto) {
        String jpql = "SELECT c FROM CarritoItem c WHERE c.producto.idProducto = :idProducto";
        List<CarritoItem> resultados = entityManager
                .createQuery(jpql, CarritoItem.class)
                .setParameter("idProducto", idProducto)
                .getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public void agregarItem(CarritoItem item) {
        entityManager.persist(item);
    }

    @Override
    public void actualizarItem(CarritoItem item) {
        entityManager.merge(item);
    }

    @Override
    public void eliminarItemPorProductoId(int idProducto) {
        String jpql = "DELETE FROM CarritoItem c WHERE c.producto.idProducto = :idProducto";
        entityManager.createQuery(jpql)
                .setParameter("idProducto", idProducto)
                .executeUpdate();
    }

    @Override
    public void vaciarCarrito() {
        String jpql = "DELETE FROM CarritoItem";
        entityManager.createQuery(jpql).executeUpdate();
    }
}