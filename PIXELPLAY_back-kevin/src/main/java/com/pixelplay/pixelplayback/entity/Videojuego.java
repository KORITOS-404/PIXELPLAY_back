// Videojuego.java
package com.pixelplay.pixelplayback.entity;

import jakarta.persistence.Entity; // Asegúrate de importar esto
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // 🎯 FALTA ESTA ANOTACIÓN CLAVE
@Table(name = "videojuegos") // O el nombre real de tu tabla
public class Videojuego {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ... el resto de tus campos ...
}