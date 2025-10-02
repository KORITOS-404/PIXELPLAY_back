package com.pixelplay.pixelplayback.repository;

import com.pixelplay.pixelplayback.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
}
