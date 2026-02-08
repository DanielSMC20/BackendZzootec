package com.zzootec.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzootec.admin.dto.category.CategoryRequestDto;
import com.zzootec.admin.dto.category.CategoryResponseDto;
import com.zzootec.admin.service.CategoryService;
import com.zzootec.admin.util.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // =======================
    // LISTAR
    // =======================
    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return categoryService.getAll();
    }

    // =======================
    // OBTENER POR ID
    // =======================
    @GetMapping("/{id}")
    public CategoryResponseDto getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    // =======================
    // CREAR CON IMAGEN
    // =======================
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CategoryResponseDto create(
            @RequestPart("data") String data,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {

        CategoryRequestDto request =
                objectMapper.readValue(data, CategoryRequestDto.class);

        if (file != null && !file.isEmpty()) {
            String imagePath = fileStorageService.saveImage(file, "categories");
            request.setImageUrl(imagePath);
        }

        return categoryService.create(request);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public CategoryResponseDto update(
            @PathVariable Long id,
            @RequestPart("data") String data,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {

        CategoryRequestDto request =
                objectMapper.readValue(data, CategoryRequestDto.class);

        if (file != null && !file.isEmpty()) {
            String imagePath = fileStorageService.saveImage(file, "categories");
            request.setImageUrl(imagePath);
        }

        return categoryService.update(id, request);
    }

    // =======================
    // DELETE LÓGICO
    // =======================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
