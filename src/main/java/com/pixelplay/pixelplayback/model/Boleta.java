package com.pixelplay.pixelplayback.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Boleta")
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBoleta;

    @OneToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    @OneToOne
    @JoinColumn(name = "idMetPago")
    private MetPago metPago;
}
