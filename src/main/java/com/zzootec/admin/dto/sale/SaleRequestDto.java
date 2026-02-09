package com.zzootec.admin.dto.sale;

import com.zzootec.admin.dto.saleitem.SaleItemRequestDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleRequestDto {
    private Long clientId;
    private String channel;
    private LocalDateTime date;
    private Double total;
    private List<SaleItemRequestDto> items;
}
