package com.zzootec.admin.dto.salesummay;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesSummaryDto {
    private Double totalSales;
    private Long totalTransactions;
    private Double averageTicket;
}