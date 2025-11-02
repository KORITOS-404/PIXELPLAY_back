package com.pixelplay.pixelplayback.dto.response;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
    private String rol;
    private Boolean activo;
}
