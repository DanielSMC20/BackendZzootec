package com.zzootec.admin.dto.sale;

import com.zzootec.admin.dto.saleitem.SaleItemRequestDto;
import lombok.Data;

import java.util.List;

@Data
public class SaleRequestDto {
    private Long clientId;
    private String channel;
    private List<SaleItemRequestDto> items;
}
