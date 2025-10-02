package com.pixelplay.pixelplayback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Usuario")
    private Integer idUsuario;

    @Column(name = "Nombre", length = 30, nullable = false)
    private String nombre;

    @Column(name = "Apellido", length = 30, nullable = false)
    private String apellido;

    @Column(name = "Direccion", length = 100)
    private String direccion;

    @Column(name = "Telefono", length = 20)
    private String telefono;

    @Column(name = "contrasena", length = 20)
    private String contrasena;
}
