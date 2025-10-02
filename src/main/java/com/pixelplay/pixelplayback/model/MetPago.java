package com.pixelplay.pixelplayback.model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "Met_Pago")
public class MetPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMetPago;

    private String tipo;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario; 
}
