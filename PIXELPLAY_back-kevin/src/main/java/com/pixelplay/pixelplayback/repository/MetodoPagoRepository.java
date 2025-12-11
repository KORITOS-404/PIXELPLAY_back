//MetodoPagoRepository.java
package com.pixelplay.pixelplayback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pixelplay.pixelplayback.entity.MetodoPago;
import com.pixelplay.pixelplayback.entity.Usuario;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
    List<MetodoPago> findByUsuarioAndActivoTrue(Usuario usuario);
    List<MetodoPago> findByUsuario_IdUsuario(Long idUsuario);
}
