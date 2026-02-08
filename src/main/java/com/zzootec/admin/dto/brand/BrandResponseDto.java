package com.zzootec.admin.dto.brand;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponseDto {
    private Long id;
    private String name;
    private String logoUrl;
    private Boolean active;
}
