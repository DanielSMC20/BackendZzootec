package com.zzootec.admin.controller;

import com.zzootec.admin.dto.sale.SaleRequestDto;
import com.zzootec.admin.dto.sale.SaleResponseDto;
import com.zzootec.admin.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public SaleResponseDto create(@RequestBody SaleRequestDto dto) {
        return saleService.create(dto);
    }

    @GetMapping
    public List<SaleResponseDto> list() {
        return saleService.findAll();
    }
}
