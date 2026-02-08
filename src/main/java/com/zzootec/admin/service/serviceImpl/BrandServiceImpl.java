package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.dto.brand.BrandRequestDto;
import com.zzootec.admin.dto.brand.BrandResponseDto;
import com.zzootec.admin.entity.Brand;
import com.zzootec.admin.repository.BrandRepository;
import com.zzootec.admin.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<BrandResponseDto> getAll() {
        return brandRepository.findByActiveTrue()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public BrandResponseDto getById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        return map(brand);
    }

    @Override
    public BrandResponseDto create(BrandRequestDto dto) {

        Brand brand = Brand.builder()
                .name(dto.getName())
                .logoUrl(dto.getLogoUrl())
                .active(true)
                .build();

        brandRepository.save(brand);
        return map(brand);
    }

    @Override
    public BrandResponseDto update(Long id, BrandRequestDto dto) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        brand.setName(dto.getName());

        if (dto.getLogoUrl() != null) {
            brand.setLogoUrl(dto.getLogoUrl());
        }

        brandRepository.save(brand);
        return map(brand);
    }

    @Override
    public void delete(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        brand.setActive(false);
        brandRepository.save(brand);
    }

    private BrandResponseDto map(Brand b) {
        return BrandResponseDto.builder()
                .id(b.getId())
                .name(b.getName())
                .logoUrl(b.getLogoUrl())
                .active(b.getActive())
                .build();
    }
}
