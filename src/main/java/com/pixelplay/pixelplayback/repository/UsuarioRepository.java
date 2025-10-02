package com.pixelplay.pixelplayback.repository;

import com.pixelplay.pixelplayback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreAndContrasena(String nombre, String contrasena);
}
