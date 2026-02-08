package com.zzootec.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombres;

    private String apellidos;

    private String telefono;

    private String correo;

    @Column(name = "canal_origen")
    private String canalOrigen; // WEB | WHATSAPP | TIENDA

    @Column(name = "image_url", length = 300)
    private String imageUrl;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
}
