//UsuarioRepository.java
package com.pixelplay.pixelplayback.repository;

import com.pixelplay.pixelplayback.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByCorreo(String correo);
    
    Boolean existsByCorreo(String correo);
    
    Page<Usuario> findByActivoTrue(Pageable pageable);
    
    @Query("SELECT u FROM Usuario u WHERE " +
           "(LOWER(u.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.apellido) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.correo) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Usuario> buscarUsuarios(@Param("keyword") String keyword, Pageable pageable);
}
