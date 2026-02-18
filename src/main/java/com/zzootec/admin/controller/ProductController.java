package com.zzootec.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzootec.admin.dto.product.ProductRequestDto;
import com.zzootec.admin.dto.product.ProductResponseDto;
import com.zzootec.admin.service.ProductService;
import com.zzootec.admin.util.FileStorageService;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;
    private final FileStorageService fileStorageService;
    private final Validator validator;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // =======================
    // LISTAR
    // =======================
    @GetMapping
    public List<ProductResponseDto> getAll() {
        return productService.getAll();
    }

    // =======================
    // OBTENER POR ID
    // =======================
    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    // =======================
    // CREAR CON IMAGEN
    // =======================
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponseDto create(
            @RequestPart("data") String data,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {

        ProductRequestDto request = objectMapper.readValue(data, ProductRequestDto.class);

        var violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(
                violations.iterator().next().getMessage()
            );
        }

        if (file != null && !file.isEmpty()) {
            String path = fileStorageService.saveImage(file, "products");
            request.setImageUrl(path);
        }

        return productService.create(request);
    }

    // =======================
    // ACTUALIZAR CON IMAGEN
    // =======================
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponseDto update(
            @PathVariable Long id,
            @RequestPart("data") String data,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {

        ProductRequestDto request = objectMapper.readValue(data, ProductRequestDto.class);

        var violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(
                violations.iterator().next().getMessage()
            );
        }

        if (file != null && !file.isEmpty()) {
            String path = fileStorageService.saveImage(file, "products");
            request.setImageUrl(path);
        }

        return productService.update(id, request);
    }

    // =======================
    // ELIMINAR (LÓGICO)
    // =======================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
    @GetMapping("/search")
    public List<ProductResponseDto> search(@RequestParam String q) {
        return productService.search(q);
    }

    // =======================
    // ENDPOINT PARA N8N: PRODUCTOS CON STOCK BAJO
    // =======================
    @GetMapping("/low-stock")
    public List<ProductResponseDto> getLowStock(@RequestParam(defaultValue = "10") int threshold) {
        return productService.getAll().stream()
            .filter(p -> p.getStock() != null && p.getStock() < threshold)
            .toList();
    }

    // =======================
    // ENDPOINT PARA N8N: PRODUCTOS CON BAJA ROTACIÓN
    // =======================
    @GetMapping("/low-rotation")
    public List<ProductResponseDto> getLowRotation(@RequestParam(defaultValue = "30") int days) {
        // Por ahora retorna productos con stock alto (poca rotación)
        // En el futuro puedes implementar lógica basada en ventas
        return productService.getAll().stream()
            .filter(p -> p.getStock() != null && p.getStock() > 50)
            .limit(10)
            .toList();
    }

}
