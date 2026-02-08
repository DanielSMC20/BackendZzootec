package com.zzootec.admin.service;

import com.zzootec.admin.dto.product.ProductRequestDto;
import com.zzootec.admin.dto.product.ProductResponseDto;

import java.util.List;

public interface ProductService {

    List<ProductResponseDto> getAll();

    ProductResponseDto getById(Long id);

    ProductResponseDto create(ProductRequestDto request);

    ProductResponseDto update(Long id, ProductRequestDto request);

    void delete(Long id);
    List<ProductResponseDto> search(String name);


}
