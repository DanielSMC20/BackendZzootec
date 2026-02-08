package com.zzootec.admin.dto.client;

import com.zzootec.admin.entity.Client;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDto {

    private Long id;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String canalOrigen;
    private LocalDateTime fechaRegistro;

    // ✅ ESTE MÉTODO FALTABA
    public static ClientResponseDto from(Client client) {
        return ClientResponseDto.builder()
                .id(client.getId())
                .nombres(client.getNombres())
                .apellidos(client.getApellidos())
                .telefono(client.getTelefono())
                .correo(client.getCorreo())
                .canalOrigen(client.getCanalOrigen())
                .fechaRegistro(client.getFechaRegistro())
                .build();
    }
}
