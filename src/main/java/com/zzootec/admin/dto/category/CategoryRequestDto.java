package com.zzootec.admin.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryRequestDto {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String name;
    
    @Size(max = 200, message = "La descripción no puede superar 200 caracteres")
    private String description;
    
    private String imageUrl;
    private Boolean active;

}
