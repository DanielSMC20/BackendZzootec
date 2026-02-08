package com.zzootec.admin.dto.product;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150)
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 300)
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private Double price;

    // ✅ CATEGORÍA
    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;

    // ✅ MARCA (FALTABA)
    @NotNull(message = "La marca es obligatoria")
    private Long brandId;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    private String imageUrl;
}
