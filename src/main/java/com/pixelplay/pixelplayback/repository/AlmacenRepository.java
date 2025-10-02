package com.pixelplay.pixelplayback.repository;

import com.pixelplay.pixelplayback.model.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {
}
