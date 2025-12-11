package com.pixelplay.pixelplayback.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    
    // 🎯 Campo AGREGADO para mapear el ID de Categoría (asumiendo que es un Long)
    @Column(name = "id_categoria")
    private Long idCategoria; 
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 300)
    private String descripcion;
    
    @Column(length = 20)
    private String genero;
    
    @Column(length = 50)
    private String plataforma;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(nullable = false)
    private Integer stock = 0;
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @Column(nullable = false)
    private Boolean activo = true;
}