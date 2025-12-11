//CrearPedidoRequest.java
package com.pixelplay.pixelplayback.dto.request;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CrearPedidoRequest {
    
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
    private String metodoPago;
    private List<DetallePedidoRequest> detalles;
    
    @Data
    @Getter
    @Setter
    public static class DetallePedidoRequest {
        private Long idProducto;
        private Integer cantidad;
    }
}
