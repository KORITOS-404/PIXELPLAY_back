//ProductoController.java
package com.pixelplay.pixelplayback.controller;

import com.pixelplay.pixelplayback.dto.response.ProductoDTO;
import com.pixelplay.pixelplayback.service.ProductoService;
import com.pixelplay.pixelplayback.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private CacheService cacheService;

    @GetMapping("/activos")
    public ResponseEntity<Page<ProductoDTO>> listarActivos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productoService.listarActivos(page, size));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ProductoDTO>> buscar(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productoService.buscar(keyword, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.crear(productoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(
            @PathVariable Long id,
            @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.actualizar(id, productoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.ok().build();
    }
    
    // ✅ NUEVO ENDPOINT: GÉNEROS CON GUAVA CACHE
    @GetMapping("/generos")
    public ResponseEntity<Map<String, Object>> obtenerGeneros() {
        long inicio = System.nanoTime();
        List<String> generos = cacheService.obtenerGeneros();
        long fin = System.nanoTime();
        
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("generos", generos);
        respuesta.put("cache", true);
        respuesta.put("mensaje", "Géneros obtenidos desde caché Guava (expira en 10 min)");
        respuesta.put("tiempo_ns", fin - inicio);
        
        return ResponseEntity.ok(respuesta);
    }
}
