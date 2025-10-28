package com.pixelplay.pixelplayback.repository;

import com.pixelplay.pixelplayback.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByPedido_IdPedido(Long idPedido);
    Boolean existsByPedido_IdPedido(Long idPedido);
}
