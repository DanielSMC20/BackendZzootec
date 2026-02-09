package com.zzootec.admin.service;

import com.zzootec.admin.dto.inventory.InventoryMovementResponseDto;
import com.zzootec.admin.entity.Producto;

import java.util.List;

public interface InventoryMovementService {

    void registerEntry(Producto product, Integer quantity, String origin);

    void registerExit(Producto product, Integer quantity, String origin);

    List<InventoryMovementResponseDto> findByProduct(Long productId);

    List<InventoryMovementResponseDto> findAll();
}
