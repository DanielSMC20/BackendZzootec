package com.zzootec.admin.service;

import com.zzootec.admin.dto.sale.SaleRequestDto;
import com.zzootec.admin.dto.sale.SaleResponseDto;

import java.util.List;

public interface SaleService {
    public SaleResponseDto create(SaleRequestDto dto);
    List<SaleResponseDto> findAll();
}