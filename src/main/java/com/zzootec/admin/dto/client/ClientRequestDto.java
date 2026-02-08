package com.zzootec.admin.dto.client;

import lombok.Data;

@Data
public class ClientRequestDto {

    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String canalOrigen;
}
