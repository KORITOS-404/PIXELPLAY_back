package com.pixelplay.pixelplayback.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Almacen")
public class Almacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAlmacen;

    @OneToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @Column(name = "cod_barras")
    private String codBarras;

    private Integer stock;
}
