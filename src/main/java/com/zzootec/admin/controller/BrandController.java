package com.zzootec.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzootec.admin.dto.brand.BrandRequestDto;
import com.zzootec.admin.dto.brand.BrandResponseDto;
import com.zzootec.admin.service.BrandService;
import com.zzootec.admin.util.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/brands")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BrandController {

    private final BrandService brandService;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public List<BrandResponseDto> getAll() {
        return brandService.getAll();
    }

    @GetMapping("/{id}")
    public BrandResponseDto getById(@PathVariable Long id) {
        return brandService.getById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BrandResponseDto create(
            @RequestPart("data") String data,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {

        BrandRequestDto request =
                objectMapper.readValue(data, BrandRequestDto.class);

        if (file != null && !file.isEmpty()) {
            String path = fileStorageService.saveImage(file, "brands");
            request.setLogoUrl(path);
        }

        return brandService.create(request);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BrandResponseDto update(
            @PathVariable Long id,
            @RequestPart("data") String data,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {

        BrandRequestDto request =
                objectMapper.readValue(data, BrandRequestDto.class);

        if (file != null && !file.isEmpty()) {
            String path = fileStorageService.saveImage(file, "brands");
            request.setLogoUrl(path);
        }

        return brandService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        brandService.delete(id);
    }
}
