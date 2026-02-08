package com.zzootec.admin.mapper;

import com.zzootec.admin.dto.product.ProductResponseDto;
import com.zzootec.admin.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private ProductResponseDto mapToDto(Producto product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .active(product.getActive())
                .imageUrl(product.getImageUrl())

                // CATEGORY
                .category(
                        product.getCategory() != null
                                ? new ProductResponseDto.CategoryDto(
                                product.getCategory().getId(),
                                product.getCategory().getName(),
                                product.getCategory().getImageUrl()
                        )
                                : null
                )

                // BRAND
                .brand(
                        product.getBrand() != null
                                ? new ProductResponseDto.BrandDto(
                                product.getBrand().getId(),
                                product.getBrand().getName(),
                                product.getBrand().getLogoUrl()
                        )
                                : null
                )

                .build();
    }


}
