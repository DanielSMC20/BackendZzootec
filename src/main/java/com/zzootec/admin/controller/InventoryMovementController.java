package com.zzootec.admin.controller;

import com.zzootec.admin.entity.InventoryMovement;
import com.zzootec.admin.service.InventoryMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-movements")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InventoryMovementController {

    private final InventoryMovementService inventoryService;

    @GetMapping
    public List<InventoryMovement> list() {
        return inventoryService.findAll();
    }

    @GetMapping("/product/{id}")
    public List<InventoryMovement> byProduct(@PathVariable Long id) {
        return inventoryService.findByProduct(id);
    }
}
