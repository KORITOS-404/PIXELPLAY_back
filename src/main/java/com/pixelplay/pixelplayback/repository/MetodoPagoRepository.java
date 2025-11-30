package com.pixelplay.pixelplayback.repository;

import com.pixelplay.pixelplayback.entity.MetodoPago;
import com.pixelplay.pixelplayback.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
    List<MetodoPago> findByUsuarioAndActivoTrue(Usuario usuario);
    List<MetodoPago> findByUsuario_IdUsuario(Long idUsuario);
}
