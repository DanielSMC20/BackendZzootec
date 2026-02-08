package com.zzootec.admin.service;

import com.zzootec.admin.dto.brand.BrandRequestDto;
import com.zzootec.admin.dto.brand.BrandResponseDto;

import java.util.List;

public interface BrandService {

    List<BrandResponseDto> getAll();

    BrandResponseDto getById(Long id);

    BrandResponseDto create(BrandRequestDto dto);

    BrandResponseDto update(Long id, BrandRequestDto dto);

    void delete(Long id);
}
