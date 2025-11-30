package com.pixelplay.pixelplayback.repository;

import com.pixelplay.pixelplayback.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNombre(String nombre);
    Boolean existsByNombre(String nombre);
}
