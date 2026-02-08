package com.zzootec.admin.dto.auth;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LoginResponseDto {

    private String token;
    private String tipo; // Bearer
    private Long idUsuario;
    private String email;
    private String nombres;
    private List<String> roles;
}
