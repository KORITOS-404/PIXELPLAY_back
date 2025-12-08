package com.pixelplay.pixelplayback.service;

import com.pixelplay.pixelplayback.dto.request.CrearPedidoRequest;
import com.pixelplay.pixelplayback.dto.request.ProductoPedidoDTO;
import com.pixelplay.pixelplayback.dto.response.PedidoResponseDTO;
import com.pixelplay.pixelplayback.entity.DetallePedido;
import com.pixelplay.pixelplayback.entity.Pedido;
import com.pixelplay.pixelplayback.entity.Producto;
import com.pixelplay.pixelplayback.entity.Usuario;
import com.pixelplay.pixelplayback.enums.EstadoPedido;
import com.pixelplay.pixelplayback.repository.DetallePedidoRepository;
import com.pixelplay.pixelplayback.repository.PedidoRepository;
import com.pixelplay.pixelplayback.repository.ProductoRepository;
import com.pixelplay.pixelplayback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crear un nuevo pedido desde Angular
     */
    public PedidoResponseDTO crearPedido(CrearPedidoRequest request) {
        try {
            // 1. Crear el pedido principal
            Pedido pedido = new Pedido();
            
            // Obtener usuario (si está logueado)
            if (request.getIdUsuario() != null) {
                Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                pedido.setUsuario(usuario);
            } else {
                // Si no está logueado, buscar o crear usuario "Invitado"
                Usuario invitado = usuarioRepository.findByCorreo(request.getCorreo())
                        .orElseGet(() -> {
                            Usuario nuevoInvitado = new Usuario();
                            nuevoInvitado.setCorreo(request.getCorreo());
                            nuevoInvitado.setNombre("Invitado");
                            nuevoInvitado.setApellido("Invitado");
                            nuevoInvitado.setActivo(true);
                            nuevoInvitado.setPassword("temp123");
                            return usuarioRepository.save(nuevoInvitado);
                        });
                pedido.setUsuario(invitado);
            }
            
            // Datos del pedido
            pedido.setNumeroPedido(generarNumeroPedido());
            pedido.setCliente(request.getCliente());
            pedido.setCorreo(request.getCorreo());
            pedido.setTelefono(request.getTelefono());
            pedido.setDireccion(request.getDireccion());
            pedido.setMetodoPago(request.getMetodoPago());
            pedido.setMontoTotal(request.getMontoTotal());
            pedido.setEstado(EstadoPedido.PENDIENTE);
            pedido.setFechaPedido(LocalDateTime.now());
            
            // Guardar el pedido
            Pedido pedidoGuardado = pedidoRepository.save(pedido);
            
            // 2. Crear los detalles del pedido (productos)
            Set<DetallePedido> detalles = new HashSet<>();
            
            for (ProductoPedidoDTO productoDTO : request.getProductos()) {
                // Buscar el producto en la BD
                Producto producto = productoRepository.findById(productoDTO.getIdProducto())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productoDTO.getIdProducto()));
                
                // Verificar stock
                if (producto.getStock() < productoDTO.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
                }
                
                // Crear detalle del pedido
                DetallePedido detalle = new DetallePedido();
                detalle.setPedido(pedidoGuardado);
                detalle.setProducto(producto);
                detalle.setNombreProducto(producto.getNombre());
                detalle.setCantidad(productoDTO.getCantidad());
                detalle.setPrecioUnitario(producto.getPrecio());
                
                // Actualizar stock del producto
                producto.setStock(producto.getStock() - productoDTO.getCantidad());
                productoRepository.save(producto);
                
                // Guardar detalle
                detallePedidoRepository.save(detalle);
                detalles.add(detalle);
            }
            
            pedidoGuardado.setDetalles(detalles);
            
            // 3. Preparar respuesta
            PedidoResponseDTO response = new PedidoResponseDTO();
            response.setIdPedido(pedidoGuardado.getIdPedido());
            response.setNumeroPedido(pedidoGuardado.getNumeroPedido());
            response.setCliente(pedidoGuardado.getCliente());
            response.setCorreo(pedidoGuardado.getCorreo());
            response.setMontoTotal(pedidoGuardado.getMontoTotal());
            response.setEstado(pedidoGuardado.getEstado());
            response.setFechaPedido(pedidoGuardado.getFechaPedido());
            response.setMensaje("Pedido creado exitosamente");
            
            return response;
            
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el pedido: " + e.getMessage());
        }
    }

    /**
     * Generar número de pedido único
     */
    private String generarNumeroPedido() {
        String prefix = "PED-";
        long timestamp = System.currentTimeMillis();
        int random = (int) (Math.random() * 1000);
        return prefix + timestamp + "-" + random;
    }

    /**
     * Obtener pedido por ID
     */
    public Pedido obtenerPedidoPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    /**
     * Listar todos los pedidos
     */
    public Iterable<Pedido> listarTodosPedidos() {
        return pedidoRepository.findAll();
    }

    /**
     * Actualizar estado del pedido
     */
    public Pedido actualizarEstado(Long idPedido, EstadoPedido nuevoEstado) {
        Pedido pedido = obtenerPedidoPorId(idPedido);
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
}
