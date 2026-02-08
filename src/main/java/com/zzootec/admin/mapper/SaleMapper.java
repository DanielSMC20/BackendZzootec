package com.zzootec.admin.mapper;

import com.zzootec.admin.dto.client.ClientResponseDto;
import com.zzootec.admin.dto.sale.SaleResponseDto;
import com.zzootec.admin.dto.saleitem.SaleItemResponseDto;
import com.zzootec.admin.entity.Sale;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper {

    public SaleResponseDto toDto(Sale sale) {

        return SaleResponseDto.builder()
                .id(sale.getId())
                .channel(sale.getChannel())
                .date(sale.getDate())
                .total(sale.getTotal())
                .client(
                        ClientResponseDto.from(sale.getClient())
                )
                .items(
                        sale.getItems().stream()
                                .map(i -> SaleItemResponseDto.builder()
                                        .productId(i.getProduct().getId())
                                        .productName(i.getProduct().getName())
                                        .quantity(i.getQuantity())
                                        .price(i.getPrice())
                                        .subtotal(i.getSubtotal())
                                        .build())
                                .toList()
                )
                .build();
    }
}
