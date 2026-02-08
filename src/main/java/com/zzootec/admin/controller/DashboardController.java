package com.zzootec.admin.controller;

import com.zzootec.admin.dto.monthlysales.MonthlySalesDto;
import com.zzootec.admin.dto.salesummay.SalesSummaryDto;
import com.zzootec.admin.dto.topproductDto.TopProductDto;
import com.zzootec.admin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public SalesSummaryDto summary() {
        return dashboardService.getSummary();
    }

    @GetMapping("/monthly-sales")
    public List<MonthlySalesDto> monthly() {
        return dashboardService.getMonthlySales();
    }

    @GetMapping("/top-products")
    public List<TopProductDto> topProducts() {
        return dashboardService.getTopProducts();
    }
}
