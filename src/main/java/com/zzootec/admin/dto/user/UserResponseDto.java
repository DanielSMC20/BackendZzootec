package com.zzootec.admin.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class UserResponseDto {

    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String imageUrl;
    private boolean activo;
    private Set<String> roles;
    private String telefono;
}
