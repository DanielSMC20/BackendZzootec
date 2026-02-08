package com.zzootec.admin.dto.category;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class CategoryResponseDto {

    private Long id;
    private String name;
    private String imageUrl;
    private Boolean active;
}
