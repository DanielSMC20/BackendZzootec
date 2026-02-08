package com.zzootec.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_movements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Producto product;

    private String type; // ENTRADA | SALIDA

    private Integer quantity;

    private LocalDateTime date;

    private String origin; // PEDIDO, AJUSTE, DEVOLUCION
}
