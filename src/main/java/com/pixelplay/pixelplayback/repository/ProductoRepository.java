package com.pixelplay.pixelplayback.repository;

import com.pixelplay.pixelplayback.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
