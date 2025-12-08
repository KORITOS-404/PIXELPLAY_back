package com.pixelplay.pixelplayback.controller;

import com.pixelplay.pixelplayback.dto.request.CrearPedidoRequest;
import com.pixelplay.pixelplayback.dto.response.PedidoResponseDTO;
import com.pixelplay.pixelplayback.entity.Pedido;
import com.pixelplay.pixelplayback.enums.EstadoPedido;
import com.pixelplay.pixelplayback.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /**
     * CREAR NUEVO PEDIDO (desde Angular)
     * POST http://localhost:8080/api/pedidos
     */
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody CrearPedidoRequest request) {
        try {
            PedidoResponseDTO response = pedidoService.crearPedido(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear pedido: " + e.getMessage());
        }
    }

    /**
     * OBTENER TODOS LOS PEDIDOS
     * GET http://localhost:8080/api/pedidos
     */
    @GetMapping
    public ResponseEntity<?> obtenerTodosPedidos() {
        try {
            Iterable<Pedido> pedidos = pedidoService.listarTodosPedidos();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener pedidos: " + e.getMessage());
        }
    }

    /**
     * OBTENER PEDIDO POR ID
     * GET http://localhost:8080/api/pedidos/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPedidoPorId(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.obtenerPedidoPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Pedido no encontrado: " + e.getMessage());
        }
    }

    /**
     * ACTUALIZAR ESTADO DEL PEDIDO
     * PUT http://localhost:8080/api/pedidos/1/estado?estado=ENVIADO
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id, 
            @RequestParam EstadoPedido estado) {
        try {
            Pedido pedidoActualizado = pedidoService.actualizarEstado(id, estado);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar estado: " + e.getMessage());
        }
    }
}
