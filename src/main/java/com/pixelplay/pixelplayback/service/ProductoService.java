package com.pixelplay.pixelplayback.service;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.pixelplay.pixelplayback.entity.Producto;
import com.pixelplay.pixelplayback.repository.ProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    private final Cache<Long, Producto> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public Page<Producto> listarActivos(Pageable pageable) {
        return repo.findByActivoTrue(pageable);
    }

    public Page<Producto> buscarPorGenero(String genero, Pageable pageable) {
        Preconditions.checkNotNull(genero, "El género no puede ser nulo");
        return repo.findByGeneroAndActivoTrue(genero, pageable);
    }

    public Page<Producto> buscarPorPalabra(String keyword, Pageable pageable) {
        Preconditions.checkNotNull(keyword, "La palabra clave no puede ser nula");
        return repo.searchProductos(keyword, pageable);
    }

    public Producto guardar(Producto producto) {
        Preconditions.checkNotNull(producto, "El producto no puede ser nulo");
        Preconditions.checkArgument(producto.getNombre() != null && !producto.getNombre().isEmpty(),
                "El nombre del producto es obligatorio");

        Producto guardado = repo.save(producto);
        cache.put(guardado.getIdProducto(), guardado);
        return guardado;
    }

    public Producto obtenerPorId(Long id) {
        Producto cacheado = cache.getIfPresent(id);
        if (cacheado != null) return cacheado;

        Optional<Producto> producto = repo.findById(id);
        producto.ifPresent(p -> cache.put(id, p));
        return producto.orElse(null);
    }

    public void eliminar(Long id) {
        Preconditions.checkNotNull(id, "El ID no puede ser nulo");
        repo.deleteById(id);
        cache.invalidate(id);
    }
}
