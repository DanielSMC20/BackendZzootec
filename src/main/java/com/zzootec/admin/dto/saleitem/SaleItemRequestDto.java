package com.zzootec.admin.dto.saleitem;

import lombok.Data;

@Data
public class SaleItemRequestDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double subtotal;
}
