package com.zzootec.admin.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClientRequestDto {

    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @Pattern(regexp = "^\\d{7,15}$", message = "El teléfono debe tener entre 7 y 15 dígitos")
    private String telefono;

    @Email(message = "Debe ser un email válido")
    private String correo;

    private String canalOrigen;
}
