//RecuperarPasswordService.java
package com.pixelplay.pixelplayback.service;

import com.pixelplay.pixelplayback.entity.Usuario;
import com.pixelplay.pixelplayback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class RecuperarPasswordService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<String, CodigoRecuperacion> codigosActivos = new HashMap<>();

public void enviarCodigoRecuperacion(String correo) {
    System.out.println("🔍 PASO 1: Buscando usuario con correo: [" + correo + "]");
    
    Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    System.out.println("✅ PASO 2: Usuario encontrado: " + usuario.getNombre());

    String codigo = generarCodigo();
    System.out.println("🔢 PASO 3: Código generado: " + codigo);

    codigosActivos.put(correo, new CodigoRecuperacion(codigo, LocalDateTime.now().plusMinutes(10)));
    System.out.println("💾 PASO 4: Código guardado en memoria");

    try {
        emailService.enviarCodigoRecuperacion(correo, codigo);
        System.out.println("📧 PASO 5: Email enviado correctamente a " + correo);
    } catch (Exception e) {
        System.err.println("❌ ERROR AL ENVIAR EMAIL: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error al enviar email: " + e.getMessage());
    }

    System.out.println("✅ PROCESO COMPLETADO");
}


    public boolean validarCodigo(String correo, String codigo) {
        CodigoRecuperacion codigoGuardado = codigosActivos.get(correo);

        if (codigoGuardado == null) {
            return false;
        }

        if (LocalDateTime.now().isAfter(codigoGuardado.getExpiracion())) {
            codigosActivos.remove(correo);
            return false;
        }

        return codigoGuardado.getCodigo().equals(codigo);
    }

    public void cambiarPassword(String correo, String codigo, String nuevaPassword) {
        if (!validarCodigo(correo, codigo)) {
            throw new RuntimeException("Código inválido o expirado");
        }

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(usuario);

        codigosActivos.remove(correo);

        System.out.println("✅ Contraseña actualizada para: " + correo);
    }

    private String generarCodigo() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }

    private static class CodigoRecuperacion {
        private String codigo;
        private LocalDateTime expiracion;

        public CodigoRecuperacion(String codigo, LocalDateTime expiracion) {
            this.codigo = codigo;
            this.expiracion = expiracion;
        }

        public String getCodigo() {
            return codigo;
        }

        public LocalDateTime getExpiracion() {
            return expiracion;
        }
    }
}
