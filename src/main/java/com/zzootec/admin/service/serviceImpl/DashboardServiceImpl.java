package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.dto.monthlysales.MonthlySalesDto;
import com.zzootec.admin.dto.salesummay.SalesSummaryDto;
import com.zzootec.admin.dto.topproductDto.TopProductDto;
import com.zzootec.admin.repository.DashboardRepository;
import com.zzootec.admin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository repository;

    @Override
    public SalesSummaryDto getSummary() {

        Object[] r = repository.summary();

        return new SalesSummaryDto(
                (Double) r[0],
                (Long) r[1],
                (Double) r[2]
        );
    }

    @Override
    public List<MonthlySalesDto> getMonthlySales() {

        return repository.salesByMonth()
                .stream()
                .map(r -> new MonthlySalesDto(
                        "Mes " + r[0],
                        (Double) r[1]
                ))
                .toList();
    }

    @Override
    public List<TopProductDto> getTopProducts() {

        return repository.topProducts()
                .stream()
                .map(r -> new TopProductDto(
                        (String) r[0],
                        (Long) r[1]
                ))
                .toList();
    }
}