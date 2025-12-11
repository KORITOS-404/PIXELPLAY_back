//AuthResponse.java
package com.pixelplay.pixelplayback.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private String nombre;
    private String apellido;
    private String correo;
    private String rol;
    private String telefono;
    private String direccion;
    private Long idUsuario;
    private String mensaje;
    
    public AuthResponse(String token, String nombre, String apellido, String correo, String rol) {
        this.token = token;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.rol = rol;
    }
}
