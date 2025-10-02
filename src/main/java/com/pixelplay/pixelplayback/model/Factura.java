package com.pixelplay.pixelplayback.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFactura;

    @OneToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    private String razonSocial;
    private Integer ruc;
}

