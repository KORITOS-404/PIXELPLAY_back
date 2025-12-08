package com.pixelplay.pixelplayback.dto.response;

import com.pixelplay.pixelplayback.enums.EstadoPedido;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
    
    private Long idPedido;
    private String numeroPedido;
    private String cliente;
    private String correo;
    private BigDecimal montoTotal;
    private EstadoPedido estado;
    private LocalDateTime fechaPedido;
    private String mensaje;
}
