package com.zzootec.admin.repository;

import com.zzootec.admin.entity.InventoryMovement;
import com.zzootec.admin.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryMovementRepository
        extends JpaRepository<InventoryMovement, Long> {

    List<InventoryMovement> findByProduct(Producto product);

    List<InventoryMovement> findByType(String type);
}
