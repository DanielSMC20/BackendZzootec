package com.zzootec.admin.dto.monthlysales;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlySalesDto {
    private String month;
    private Double total;
}