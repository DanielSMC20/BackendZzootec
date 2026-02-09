package com.zzootec.admin.controller;

import com.zzootec.admin.dto.inventory.InventoryMovementRequestDto;
import com.zzootec.admin.dto.inventory.InventoryMovementResponseDto;
import com.zzootec.admin.entity.Producto;
import com.zzootec.admin.repository.ProductoRepository;
import com.zzootec.admin.service.InventoryMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-movements")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InventoryMovementController {

    private final InventoryMovementService inventoryService;
    private final ProductoRepository productoRepository;

    // =======================
    // LISTAR TODOS
    // =======================
    @GetMapping
    public List<InventoryMovementResponseDto> list() {
        return inventoryService.findAll();
    }

    // =======================
    // LISTAR POR PRODUCTO
    // =======================
    @GetMapping("/product/{id}")
    public ResponseEntity<?> byProduct(@PathVariable Long id) {
        // Verificar si el producto existe
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Producto con ID " + id + " no encontrado"));
        }
        
        List<InventoryMovementResponseDto> movements = inventoryService.findByProduct(id);
        return ResponseEntity.ok(movements);
    }

    // =======================
    // REGISTRAR ENTRADA
    // =======================
    @PostMapping("/entry")
    public ResponseEntity<?> registerEntry(@Valid @RequestBody InventoryMovementRequestDto request) {
        Producto product = productoRepository.findById(request.getProductId())
                .orElse(null);
        
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Producto con ID " + request.getProductId() + " no encontrado"));
        }

        inventoryService.registerEntry(product, request.getQuantity(), request.getOrigin());

        return ResponseEntity.ok()
                .body(new MessageResponse("Entrada registrada exitosamente. Nuevo stock: " + product.getStock()));
    }

    // =======================
    // REGISTRAR SALIDA
    // =======================
    @PostMapping("/exit")
    public ResponseEntity<?> registerExit(@Valid @RequestBody InventoryMovementRequestDto request) {
        Producto product = productoRepository.findById(request.getProductId())
                .orElse(null);
        
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Producto con ID " + request.getProductId() + " no encontrado"));
        }

        try {
            inventoryService.registerExit(product, request.getQuantity(), request.getOrigin());
            return ResponseEntity.ok()
                    .body(new MessageResponse("Salida registrada exitosamente. Nuevo stock: " + product.getStock()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    // Clase interna para respuesta simple
    record MessageResponse(String message) {}
}
