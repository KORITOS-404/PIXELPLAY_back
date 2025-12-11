package com.pixelplay.pixelplayback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pixelplay.pixelplayback.entity.Videojuego;

// Repositorio de Spring Data JPA para la entidad Videojuego.
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {

    // ❌ ¡AQUÍ NO DEBE HABER MÉTODOS DE STOCK! 
    // Los métodos de consulta son específicos para la entidad Videojuego.

    // Puedes agregar aquí otros métodos específicos para Videojuego si los necesitas.
}