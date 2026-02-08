package com.zzootec.admin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sale_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Sale sale;

    @ManyToOne
    private Producto product;

    private Integer quantity;

    private Double price;

    private Double subtotal;
}
