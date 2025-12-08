package com.pixelplay.pixelplayback.dto.request;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearPedidoRequest {
    
    private String cliente;
    private String correo;
    private String telefono;
    private String direccion;
    private String metodoPago;
    private List<ProductoPedidoDTO> productos;
    private BigDecimal montoTotal;
    private Long idUsuario;
}
