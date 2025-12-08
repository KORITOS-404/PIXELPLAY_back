package com.pixelplay.pixelplayback.service;

import com.pixelplay.pixelplayback.dto.response.ProductoDTO;
import com.pixelplay.pixelplayback.entity.Producto;
import com.pixelplay.pixelplayback.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Page<ProductoDTO> listarActivos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productoRepository.findByActivoTrue(pageable)
                .map(this::convertirADTO);
    }

    public Page<ProductoDTO> buscar(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productoRepository.buscarProductos(keyword, pageable)
                .map(this::convertirADTO);
    }

    public ProductoDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return convertirADTO(producto);
    }

    public ProductoDTO crear(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setGenero(productoDTO.getGenero());
        producto.setPrecio(productoDTO.getPrecio());
        // ❌ ELIMINAR: producto.setCodigoBarras(productoDTO.getCodigoBarras());
        producto.setStock(productoDTO.getStock());
        producto.setImageUrl(productoDTO.getImageUrl());
        producto.setPlataforma(productoDTO.getPlataforma()); // ✨ AGREGAR si tienes en DTO
        producto.setActivo(true);
        
        Producto guardado = productoRepository.save(producto);
        return convertirADTO(guardado);
    }

    public ProductoDTO actualizar(Long id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setGenero(productoDTO.getGenero());
        producto.setPrecio(productoDTO.getPrecio());
        // ❌ ELIMINAR: producto.setCodigoBarras(productoDTO.getCodigoBarras());
        producto.setStock(productoDTO.getStock());
        producto.setImageUrl(productoDTO.getImageUrl());
        producto.setPlataforma(productoDTO.getPlataforma()); // ✨ AGREGAR si tienes en DTO
        
        Producto actualizado = productoRepository.save(producto);
        return convertirADTO(actualizado);
    }

    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    private ProductoDTO convertirADTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setGenero(producto.getGenero());
        dto.setPrecio(producto.getPrecio());
        // ❌ ELIMINAR: dto.setCodigoBarras(producto.getCodigoBarras());
        dto.setStock(producto.getStock());
        dto.setImageUrl(producto.getImageUrl());
        dto.setPlataforma(producto.getPlataforma()); // ✨ AGREGAR si tienes en DTO
        dto.setActivo(producto.getActivo());
        return dto;
    }
}
