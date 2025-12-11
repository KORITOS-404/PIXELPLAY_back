//FacturaRepository.java
package com.pixelplay.pixelplayback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pixelplay.pixelplayback.entity.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByPedido_IdPedido(Long idPedido);
    Boolean existsByPedido_IdPedido(Long idPedido);
}
