package com.example.demo.repository;

import com.example.demo.model.CarritoItem;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, Integer> {

    Optional<CarritoItem> findByProducto_IdProducto(Integer idProducto);
}