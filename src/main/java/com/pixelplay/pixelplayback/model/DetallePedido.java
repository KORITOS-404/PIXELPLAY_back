package com.pixelplay.pixelplayback.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Detalle_Pedido")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetallePedido;

    @ManyToOne
    @JoinColumn(name = "idAlmacen")
    private Almacen almacen;

    private Integer cantidad;
    private Double precio;
}
