//PedidoService.java
package com.pixelplay.pixelplayback.service;

import com.pixelplay.pixelplayback.dto.request.CrearPedidoRequest;
import com.pixelplay.pixelplayback.dto.response.PedidoResponseDTO;
import com.pixelplay.pixelplayback.entity.DetallePedido;
import com.pixelplay.pixelplayback.entity.Pedido;
import com.pixelplay.pixelplayback.entity.Producto;
import com.pixelplay.pixelplayback.entity.Usuario;
import com.pixelplay.pixelplayback.enums.EstadoPedido;
import com.pixelplay.pixelplayback.repository.PedidoRepository;
import com.pixelplay.pixelplayback.repository.ProductoRepository;
import com.pixelplay.pixelplayback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public PedidoResponseDTO crearPedido(CrearPedidoRequest request) {
        Usuario usuario = null;
        if (request.getIdUsuario() != null) {
            usuario = usuarioRepository.findById(request.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        }

        Pedido pedido = new Pedido();
        pedido.setNumeroPedido(generarNumeroPedido());
        pedido.setUsuario(usuario);
        pedido.setNombre(request.getNombre());
        pedido.setApellido(request.getApellido());
        pedido.setCorreo(request.getCorreo());
        pedido.setTelefono(request.getTelefono());
        pedido.setDireccion(request.getDireccion());
        pedido.setMetadoPago(request.getMetodoPago());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setFechaPedido(LocalDateTime.now());

        BigDecimal montoTotal = BigDecimal.ZERO;
        List<DetallePedido> detalles = new ArrayList<>();

        for (var item : request.getDetalles()) {
            Producto producto = productoRepository.findById(item.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getIdProducto()));

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            
            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad()));
            detalle.setSubtotal(subtotal);
            
            montoTotal = montoTotal.add(subtotal);
            detalles.add(detalle);
        }

        pedido.setMontoTotal(montoTotal);
        pedido.setDetalles(detalles);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setIdPedido(pedidoGuardado.getIdPedido());
        response.setNumeroPedido(pedidoGuardado.getNumeroPedido());
        response.setMontoTotal(pedidoGuardado.getMontoTotal());
        response.setEstado(pedidoGuardado.getEstado());
        response.setMensaje("Pedido creado exitosamente");

        return response;
    }

    public Iterable<Pedido> listarTodosPedidos() {
        return pedidoRepository.findAll();
    }

    public Page<Pedido> listarPedidosPaginados(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    public Page<Pedido> buscarPedidos(String keyword, Pageable pageable) {
        Page<Pedido> resultado = pedidoRepository.findByNumeroPedidoContainingIgnoreCase(keyword, pageable);
        
        if (resultado.isEmpty()) {
            resultado = pedidoRepository.findByCorreoContainingIgnoreCase(keyword, pageable);
        }
        
        if (resultado.isEmpty()) {
            resultado = pedidoRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
                    keyword, keyword, pageable);
        }
        
        return resultado;
    }

    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    @Transactional
    public Pedido actualizarEstado(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = obtenerPedidoPorId(id);
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void eliminarPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado con ID: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    private String generarNumeroPedido() {
        return "PED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<Pedido> obtenerPedidosPorUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuario.getPedidos();
    }
}
