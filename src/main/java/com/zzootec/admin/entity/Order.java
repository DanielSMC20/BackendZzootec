package com.zzootec.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    @Column(nullable = false)
    private String channel; // WEB | WHATSAPP | TIENDA

    @Column(nullable = false)
    private String status; // NUEVO, ATENCION, RESERVADO...

    private Double total;

    private LocalDateTime fecha;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}
