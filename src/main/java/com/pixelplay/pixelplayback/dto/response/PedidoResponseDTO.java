package com.pixelplay.pixelplayback.dto.response;

import com.pixelplay.pixelplayback.enums.EstadoPedido;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

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
