// RoleRepository.java

package com.pixelplay.pixelplayback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // ⬅️ Asegúrate de tener este import
import org.springframework.data.repository.query.Param; // ⬅️ Asegúrate de tener este import
import org.springframework.stereotype.Repository;

import com.pixelplay.pixelplayback.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNombre(String nombre);
    Boolean existsByNombre(String nombre);
    
    // ➡️ ESTA LÍNEA DEBE SER AGREGADA
    @Query("SELECT r FROM Role r WHERE r.nombre = :name")
    Optional<Role> findByName(@Param("name") String name); 
}