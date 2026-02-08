package com.zzootec.admin.dto.saleitem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleItemResponseDto {

    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double subtotal;
}
