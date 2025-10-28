package com.pixelplay.pixelplayback.repository;

import com.pixelplay.pixelplayback.entity.Pedido;
import com.pixelplay.pixelplayback.entity.Usuario;
import com.pixelplay.pixelplayback.enums.EstadoPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Pedidos de un usuario
    Page<Pedido> findByUsuario(Usuario usuario, Pageable pageable);
    List<Pedido> findByUsuario_IdUsuario(Long idUsuario);
    
    // Pedidos por estado
    Page<Pedido> findByEstado(EstadoPedido estado, Pageable pageable);
    
    // Pedidos de un usuario por estado
    List<Pedido> findByUsuarioAndEstado(Usuario usuario, EstadoPedido estado);
}
