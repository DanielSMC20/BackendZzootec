package com.zzootec.admin.dto.sale;

import com.zzootec.admin.dto.client.ClientResponseDto;
import com.zzootec.admin.dto.saleitem.SaleItemResponseDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SaleResponseDto {

    private Long id;
    private String channel;
    private LocalDateTime date;
    private Double total;

    private ClientResponseDto client;
    private List<SaleItemResponseDto> items;
}
