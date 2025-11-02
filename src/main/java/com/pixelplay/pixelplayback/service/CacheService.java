package com.pixelplay.pixelplayback.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    // ✅ USO DE GUAVA - Cache con expiración
    private final LoadingCache<String, List<String>> generoCache;

    public CacheService() {
        this.generoCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(new CacheLoader<String, List<String>>() {
                    @Override
                    public List<String> load(String key) {
                        return cargarGeneros();
                    }
                });
    }

    private List<String> cargarGeneros() {
        // ✅ USO DE GUAVA - Lists
        return Lists.newArrayList(
                "Videojuegos",
                "Consolas",
                "Accesorios",
                "Merchandising"
        );
    }

    public List<String> obtenerGeneros() {
        try {
            return generoCache.get("generos");
        } catch (ExecutionException e) {
            return cargarGeneros();
        }
    }
}
