package com.example.demo.dao;

import com.example.demo.model.Cita;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CitaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void agregar(Cita cita) {
        entityManager.persist(cita);
    }

    public List<Cita> obtenerTodas() {
        String jpql = "SELECT c FROM Cita c";
        return entityManager.createQuery(jpql, Cita.class).getResultList();
    }

    public void eliminar(int idCita) {
        Cita cita = entityManager.find(Cita.class, idCita);
        if (cita != null) {
            entityManager.remove(cita);
        }
    }
}
