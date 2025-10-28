package com.pixelplay.pixelplayback.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 300)
    private String descripcion;
    
    @Column(length = 20)
    private String genero; // Ej: "Videojuegos", "Consolas", "Accesorios"
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(name = "codigo_barras", unique = true, length = 30)
    private String codigoBarras;
    
    @Column(nullable = false)
    private Integer stock = 0;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(nullable = false)
    private Boolean activo = true;
}
