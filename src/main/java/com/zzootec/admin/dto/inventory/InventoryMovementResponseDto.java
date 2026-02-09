package com.zzootec.admin.dto.inventory;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryMovementResponseDto {

    private Long id;
    private ProductDto product;
    private String type; // ENTRADA | SALIDA
    private Integer quantity;
    private LocalDateTime date;
    private String origin; // PEDIDO | AJUSTE | DEVOLUCION

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductDto {
        private Long id;
        private String name;
        private String imageUrl;
    }
}
