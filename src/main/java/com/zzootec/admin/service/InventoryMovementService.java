package com.zzootec.admin.service;

import com.zzootec.admin.entity.InventoryMovement;
import com.zzootec.admin.entity.Producto;

import java.util.List;

public interface InventoryMovementService {

    void registerEntry(Producto product, Integer quantity, String origin);

    void registerExit(Producto product, Integer quantity, String origin);

    List<InventoryMovement> findByProduct(Long productId);

    List<InventoryMovement> findAll();
}
