package com.pixelplay.pixelplayback.repository;

import com.pixelplay.pixelplayback.entity.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    Optional<Boleta> findByPedido_IdPedido(Long idPedido);
    Boolean existsByPedido_IdPedido(Long idPedido);
}
