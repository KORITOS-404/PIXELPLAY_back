package com.pixelplay.pixelplayback.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")

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

    // Getters y setters
}
