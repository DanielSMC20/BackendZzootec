package com.zzootec.admin.service;

import com.zzootec.admin.dto.category.CategoryRequestDto;
import com.zzootec.admin.dto.category.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto create(CategoryRequestDto dto);

    List<CategoryResponseDto> getAll();

    CategoryResponseDto getById(Long id);

    CategoryResponseDto update(Long id, CategoryRequestDto dto);

    void delete(Long id);
}
