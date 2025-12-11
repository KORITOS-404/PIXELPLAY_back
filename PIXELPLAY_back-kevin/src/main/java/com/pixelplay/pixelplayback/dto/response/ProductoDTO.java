//ProductoDTO.java
package com.pixelplay.pixelplayback.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private String genero;
    private BigDecimal precio;
    private String plataforma;
    private Integer stock;
    private String imageUrl;
    private Boolean activo;
}
