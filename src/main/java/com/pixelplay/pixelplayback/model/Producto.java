package com.pixelplay.pixelplayback.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    private String nombre;
    private String genero;
    private String descripcion;
}
