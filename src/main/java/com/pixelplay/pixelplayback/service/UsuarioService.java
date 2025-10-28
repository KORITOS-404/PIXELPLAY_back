package com.pixelplay.pixelplayback.service;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.pixelplay.pixelplayback.entity.Usuario;
import com.pixelplay.pixelplayback.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    private final Cache<Long, Usuario> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        Preconditions.checkNotNull(usuario, "El usuario no puede ser nulo");
        Preconditions.checkArgument(usuario.getCorreo() != null && !usuario.getCorreo().isEmpty(),
                "El correo del usuario es obligatorio");
        Preconditions.checkArgument(usuario.getNombre() != null && !usuario.getNombre().isEmpty(),
                "El nombre del usuario es obligatorio");

        if (repo.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario guardado = repo.save(usuario);
        cache.put(guardado.getIdUsuario(), guardado);
        return guardado;
    }

    public Usuario obtenerPorId(Long id) {
        Usuario cacheado = cache.getIfPresent(id);
        if (cacheado != null) return cacheado;

        Optional<Usuario> usuario = repo.findById(id);
        usuario.ifPresent(u -> cache.put(id, u));
        return usuario.orElse(null);
    }

    public Usuario obtenerPorCorreo(String correo) {
        return repo.findByCorreo(correo).orElse(null);
    }

    public void eliminar(Long id) {
        Preconditions.checkNotNull(id, "El ID no puede ser nulo");
        repo.deleteById(id);
        cache.invalidate(id);
    }
}
