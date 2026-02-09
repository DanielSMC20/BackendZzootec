package com.zzootec.admin.mapper;

import com.zzootec.admin.dto.inventory.InventoryMovementResponseDto;
import com.zzootec.admin.entity.InventoryMovement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventoryMovementMapper {

    public InventoryMovementResponseDto toDto(InventoryMovement entity) {
        if (entity == null) {
            return null;
        }

        return InventoryMovementResponseDto.builder()
                .id(entity.getId())
                .type(entity.getType())
                .quantity(entity.getQuantity())
                .date(entity.getDate())
                .origin(entity.getOrigin())
                .product(InventoryMovementResponseDto.ProductDto.builder()
                        .id(entity.getProduct().getId())
                        .name(entity.getProduct().getName())
                        .imageUrl(entity.getProduct().getImageUrl())
                        .build())
                .build();
    }

    public List<InventoryMovementResponseDto> toDtoList(List<InventoryMovement> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
