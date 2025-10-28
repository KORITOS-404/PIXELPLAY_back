package com.pixelplay.pixelplayback.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "boletas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Boleta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boleta")
    private Long idBoleta;
    
    @OneToOne
    @JoinColumn(name = "id_pedido", nullable = false, unique = true)
    private Pedido pedido;
    
    @Column(length = 8)
    private String dni; // DNI del cliente
}
