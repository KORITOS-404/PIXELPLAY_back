//PedidoRepository.java
package com.pixelplay.pixelplayback.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pixelplay.pixelplayback.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    Page<Pedido> findByNumeroPedidoContainingIgnoreCase(String numeroPedido, Pageable pageable);
    
    Page<Pedido> findByCorreoContainingIgnoreCase(String correo, Pageable pageable);
    
    Page<Pedido> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
            String nombre, String apellido, Pageable pageable);
}
