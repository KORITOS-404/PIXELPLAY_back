package com.pixelplay.pixelplayback.service;

import com.pixelplay.pixelplayback.model.Usuario;
import com.pixelplay.pixelplayback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Registrar un nuevo usuario
     * @param usuario Usuario a registrar
     * @return Usuario guardado en la base de datos
     */
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getContrasena() == null) {
            throw new IllegalArgumentException("Nombre y contraseña son obligatorios");
        }
        return usuarioRepository.save(usuario);
    }

    /**
     * Login: buscar usuario por nombre y contraseña
     * @param nombre Nombre del usuario
     * @param contrasena Contraseña del usuario
     * @return Optional con el usuario si existe, vacío si no
     */
    public Optional<Usuario> login(String nombre, String contrasena) {
        return usuarioRepository.findByNombreAndContrasena(nombre, contrasena);
    }
}
