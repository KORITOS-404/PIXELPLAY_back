//BoletaRepository.java
package com.pixelplay.pixelplayback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pixelplay.pixelplayback.entity.Boleta;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    Optional<Boleta> findByPedido_IdPedido(Long idPedido);
    Boolean existsByPedido_IdPedido(Long idPedido);
}
