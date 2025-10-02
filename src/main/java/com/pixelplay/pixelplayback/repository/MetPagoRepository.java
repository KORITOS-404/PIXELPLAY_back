package com.pixelplay.pixelplayback.repository;

import com.pixelplay.pixelplayback.model.MetPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetPagoRepository extends JpaRepository<MetPago, Long> {
}
