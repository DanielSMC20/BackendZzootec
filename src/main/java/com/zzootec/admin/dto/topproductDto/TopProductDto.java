package com.zzootec.admin.dto.topproductDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopProductDto {
    private String product;
    private Long quantity;
}
