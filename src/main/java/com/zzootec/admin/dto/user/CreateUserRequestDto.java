package com.zzootec.admin.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CreateUserRequestDto {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    private String email;

    @Pattern(regexp = "^\\d{9}$", message = "El teléfono debe tener exactamente 9 dígitos")
    private String telefono;

    private String nombres;
    private String apellidos;
    private String password;
    private List<String> roles;
    private LocalDate fechaNacimiento;
    private String imageUrl;

    private Boolean activo;
}

