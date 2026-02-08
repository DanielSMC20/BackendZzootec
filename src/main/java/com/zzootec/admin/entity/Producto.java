package com.zzootec.admin.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_product_category", columnList = "category_id"),
                @Index(name = "idx_product_brand", columnList = "brand_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private double price;

    private Integer stock;

    private String imageUrl;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;

    private Boolean active = true;
}
