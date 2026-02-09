package com.zzootec.admin.mapper;

import com.zzootec.admin.dto.category.CategoryResponseDto;
import com.zzootec.admin.entity.Category;
import org.springframework.stereotype.Component;

@Component

public class CategoryMapper {

    public CategoryResponseDto toResponse(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .imageUrl(category.getImageUrl())
                .active(category.getActive())
                .build();
    }
}
