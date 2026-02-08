package com.zzootec.admin.dto.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Boolean active;
    private String imageUrl;

    // ✅ categoría
    private CategoryDto category;

    // ✅ marca (FALTABA)
    private BrandDto brand;

    // ============================
    // CATEGORY DTO
    // ============================
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryDto {
        private Long id;
        private String name;
        private String imageUrl;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BrandDto {
        private Long id;
        private String name;
        private String imageUrl;
    }
}
