package com.pixelplay.pixelplayback.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "facturas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;
    
    @OneToOne
    @JoinColumn(name = "id_pedido", nullable = false, unique = true)
    private Pedido pedido;
    
    @Column(name = "razon_social", nullable = false, length = 100)
    private String razonSocial;
    
    @Column(nullable = false, length = 11)
    private String ruc; // RUC como String para evitar problemas con ceros iniciales
}
