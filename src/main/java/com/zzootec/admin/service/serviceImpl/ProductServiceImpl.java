package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.dto.product.ProductRequestDto;
import com.zzootec.admin.dto.product.ProductResponseDto;
import com.zzootec.admin.entity.Brand;
import com.zzootec.admin.entity.Category;
import com.zzootec.admin.entity.Producto;
import com.zzootec.admin.repository.BrandRepository;
import com.zzootec.admin.repository.CategoryRepository;
import com.zzootec.admin.repository.ProductoRepository;
import com.zzootec.admin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductoRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;


    @Override
    public List<ProductResponseDto> getAll() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public ProductResponseDto getById(Long id) {
        Producto product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return mapToDto(product);
    }

    @Override
    public ProductResponseDto create(ProductRequestDto request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        Producto product = Producto.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .active(true)
                .category(category)
                .brand(brand) // ✅ AQUÍ
                .build();

        productRepository.save(product);
        return mapToDto(product);
    }


    @Override
    public ProductResponseDto update(Long id, ProductRequestDto request) {
        Producto product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            product.setCategory(category);
        }

        // ✅ SETEAR MARCA
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
            product.setBrand(brand);
        }

        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            product.setImageUrl(request.getImageUrl());
        }

        productRepository.save(product);
        return mapToDto(product);
    }

    @Override
    public void delete(Long id) {
        Producto product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setActive(false); // borrado lógico
        productRepository.save(product);
    }

    // =======================
    // MAPPER INTERNO
    // =======================
    private ProductResponseDto mapToDto(Producto product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .active(product.getActive())
                .imageUrl(product.getImageUrl())
                .category(
                        product.getCategory() != null
                                ? new ProductResponseDto.CategoryDto(
                                product.getCategory().getId(),
                                product.getCategory().getName(),
                                product.getCategory().getImageUrl()
                        )
                                : null
                )
                .brand(
                        product.getBrand() != null
                                ? new ProductResponseDto.BrandDto(
                                product.getBrand().getId(),
                                product.getBrand().getName(),
                                product.getBrand().getLogoUrl()
                        )
                                : null
                )
                .build();
    }


    @Override
    public List<ProductResponseDto> search(String name) {
        return productRepository.findByActiveTrueAndNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

}
