package com.zzootec.admin.service;

import com.zzootec.admin.dto.monthlysales.MonthlySalesDto;
import com.zzootec.admin.dto.salesummay.SalesSummaryDto;
import com.zzootec.admin.dto.topproductDto.TopProductDto;

import java.util.List;

public interface DashboardService {

    SalesSummaryDto getSummary();

    List<MonthlySalesDto> getMonthlySales();

    List<TopProductDto> getTopProducts();
}
