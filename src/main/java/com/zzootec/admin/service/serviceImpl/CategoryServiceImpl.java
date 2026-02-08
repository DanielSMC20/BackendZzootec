package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.dto.category.CategoryRequestDto;
import com.zzootec.admin.dto.category.CategoryResponseDto;
import com.zzootec.admin.entity.Category;
import com.zzootec.admin.repository.CategoryRepository;
import com.zzootec.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    // ==========================
    // LISTAR
    // ==========================
    @Override
    public List<CategoryResponseDto> getAll() {
        return categoryRepository.findByActiveTrue()
                .stream()
                .map(this::map)
                .toList();
    }

    // ==========================
    // OBTENER POR ID
    // ==========================
    @Override
    public CategoryResponseDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        return map(category);
    }

    // ==========================
    // CREAR
    // ==========================
    @Override
    public CategoryResponseDto create(CategoryRequestDto request) {

        Category category = Category.builder()
                .name(request.getName())
                .imageUrl(request.getImageUrl())
                .active(true)
                .build();

        categoryRepository.save(category);
        return map(category);
    }

    // ==========================
    // ACTUALIZAR
    // ==========================
    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto dto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        category.setName(dto.getName());

        if (dto.getImageUrl() != null) {
            category.setImageUrl(dto.getImageUrl());
        }

        categoryRepository.save(category);
        return map(category);
    }

    // ==========================
    // DELETE LÓGICO
    // ==========================
    @Override
    public void delete(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        category.setActive(false);
        categoryRepository.save(category);
    }

    // ==========================
    // MAPPER
    // ==========================
    private CategoryResponseDto map(Category c) {
        return CategoryResponseDto.builder()
                .id(c.getId())
                .name(c.getName())
                .imageUrl(c.getImageUrl())
                .active(c.getActive())
                .build();
    }
}
