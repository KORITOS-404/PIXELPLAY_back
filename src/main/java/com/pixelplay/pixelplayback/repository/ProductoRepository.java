package com.pixelplay.pixelplayback.repository;

import com.pixelplay.pixelplayback.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Buscar productos activos
    Page<Producto> findByActivoTrue(Pageable pageable);
    
    // Buscar por género
    Page<Producto> findByGeneroAndActivoTrue(String genero, Pageable pageable);
    
    // Buscar por nombre (búsqueda)
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND " +
           "(LOWER(p.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Producto> searchProductos(@Param("keyword") String keyword, Pageable pageable);
    
}
