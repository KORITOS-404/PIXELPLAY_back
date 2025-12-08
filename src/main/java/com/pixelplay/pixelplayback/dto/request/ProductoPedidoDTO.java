package com.pixelplay.pixelplayback.dto.request;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPedidoDTO {
    
    private Long idProducto;
    private String nombreProducto;
    private String imagenProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}
