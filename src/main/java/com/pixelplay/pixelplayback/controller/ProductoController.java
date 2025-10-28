package com.pixelplay.pixelplayback.controller;

import com.pixelplay.pixelplayback.entity.Producto;
import com.pixelplay.pixelplayback.service.ProductoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Producto>> listarActivos(Pageable pageable) {
        return ResponseEntity.ok(service.listarActivos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        Producto producto = service.obtenerPorId(id);
        return producto != null ? ResponseEntity.ok(producto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<Producto>> buscar(
            @RequestParam String keyword,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.buscarPorPalabra(keyword, pageable));
    }

    @PostMapping
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) {
        return ResponseEntity.ok(service.guardar(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
