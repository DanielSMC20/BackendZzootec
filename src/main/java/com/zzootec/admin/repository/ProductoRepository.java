package com.zzootec.admin.repository;

import com.zzootec.admin.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Solo productos activos
    List<Producto> findByActiveTrue();
    List<Producto> findByActiveTrueAndNameContainingIgnoreCase(String name);

    @Query("SELECT c.name, SUM(p.stock) " +
           "FROM Producto p " +
           "JOIN p.category c " +
           "WHERE p.active = true " +
           "GROUP BY c.name " +
           "ORDER BY SUM(p.stock) DESC")
    List<Object[]> sumStockByCategory();

}
