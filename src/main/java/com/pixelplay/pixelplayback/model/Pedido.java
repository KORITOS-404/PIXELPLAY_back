package com.pixelplay.pixelplayback.model;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "Pedido")
public class Pedido {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    @OneToOne
    private DetallePedido detallePedido;

    @OneToOne
    private MetPago metPago;

    @Temporal(TemporalType.DATE)
    private Date fechaPedido;

    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;

    private Double montoTotal;
    private Boolean documentoFiscal;
}
