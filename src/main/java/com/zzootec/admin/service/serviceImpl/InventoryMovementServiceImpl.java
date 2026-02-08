package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.entity.InventoryMovement;
import com.zzootec.admin.entity.Producto;
import com.zzootec.admin.repository.InventoryMovementRepository;
import com.zzootec.admin.repository.ProductoRepository;
import com.zzootec.admin.service.InventoryMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryMovementServiceImpl
        implements InventoryMovementService {

    private final InventoryMovementRepository movementRepository;
    private final ProductoRepository productRepository;

    @Override
    public void registerEntry(Producto product, Integer quantity, String origin) {

        product.setStock(product.getStock() + quantity);
        productRepository.save(product);

        InventoryMovement movement = InventoryMovement.builder()
                .product(product)
                .type("ENTRADA")
                .quantity(quantity)
                .origin(origin)
                .date(LocalDateTime.now())
                .build();

        movementRepository.save(movement);
    }

    @Override
    public void registerExit(Producto product, Integer quantity, String origin) {

        if (product.getStock() < quantity) {
            throw new RuntimeException("Stock insuficiente");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        InventoryMovement movement = InventoryMovement.builder()
                .product(product)
                .type("SALIDA")
                .quantity(quantity)
                .origin(origin)
                .date(LocalDateTime.now())
                .build();

        movementRepository.save(movement);
    }

    @Override
    public List<InventoryMovement> findByProduct(Long productId) {

        Producto product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return movementRepository.findByProduct(product);
    }

    @Override
    public List<InventoryMovement> findAll() {
        return movementRepository.findAll();
    }
}