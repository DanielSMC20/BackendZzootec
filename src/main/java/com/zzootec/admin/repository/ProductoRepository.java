package com.zzootec.admin.repository;

import com.zzootec.admin.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Solo productos activos
    List<Producto> findByActiveTrue();
    List<Producto> findByActiveTrueAndNameContainingIgnoreCase(String name);

}
