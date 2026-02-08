package com.zzootec.admin.dto.category;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryRequestDto {
    private String name;
    private String description;
    private String imageUrl;
}
