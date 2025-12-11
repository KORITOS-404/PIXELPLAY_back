//PedidoResponseDTO.java
package com.pixelplay.pixelplayback.dto.response;

import java.math.BigDecimal;

import com.pixelplay.pixelplayback.enums.EstadoPedido;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PedidoResponseDTO {
    
    private Long idPedido;
    private String numeroPedido;
    private BigDecimal montoTotal;
    private EstadoPedido estado;
    private String mensaje;
}
