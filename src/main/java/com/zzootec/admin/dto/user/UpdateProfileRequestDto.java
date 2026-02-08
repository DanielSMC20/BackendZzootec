package com.zzootec.admin.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequestDto {

    private String nombres;
    private String apellidos;
    private String imageUrl;
    private String password;
    private String telefono;
}
