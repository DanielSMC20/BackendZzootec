package com.zzootec.admin.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterUserRequestDto {

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotEmpty
    private List<String> roles; // ADMIN, VENTAS, ALMACEN

    // Opcional: teléfono (se añade para evitar inserciones con valor nulo)
    private String telefono;
}
