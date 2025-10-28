package com.pixelplay.pixelplayback.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private String nombre;
    private String apellido;
    private String correo;
    private String rol; // "ROLE_USER" o "ROLE_ADMIN"
    
    public AuthResponse(String token, String nombre, String apellido, String correo, String rol) {
        this.token = token;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.rol = rol;
    }
}
