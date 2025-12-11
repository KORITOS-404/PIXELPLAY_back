package com.pixelplay.pixelplayback.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pixelplay.pixelplayback.entity.Producto; // Importante para devolver una lista

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Buscar productos activos
    Page<Producto> findByActivoTrue(Pageable pageable);
    
    // Buscar por género
    Page<Producto> findByGeneroAndActivoTrue(String genero, Pageable pageable);
    
    // Búsqueda global por keyword
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND " +
           "(LOWER(p.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.genero) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Producto> buscarProductos(@Param("keyword") String keyword, Pageable pageable);

    // 🚀 MÉTODO PARA EL JOB DE DESCUENTOS (En la entidad que tiene el campo 'stock')
    // Busca todos los productos que tengan un stock menor o igual al valor dado.
    List<Producto> findByStockLessThanEqual(int stock);
}