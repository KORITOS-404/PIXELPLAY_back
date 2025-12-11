//RecuperarPasswordController.java
package com.pixelplay.pixelplayback.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pixelplay.pixelplayback.dto.request.NuevaPasswordRequest;
import com.pixelplay.pixelplayback.dto.request.RecuperarPasswordRequest;
import com.pixelplay.pixelplayback.dto.request.ValidarCodigoRequest;
import com.pixelplay.pixelplayback.service.RecuperarPasswordService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class RecuperarPasswordController {

    @Autowired
    private RecuperarPasswordService recuperarPasswordService;

    @PostMapping("/recuperar-password/enviar-codigo")
    public ResponseEntity<?> enviarCodigo(@RequestBody RecuperarPasswordRequest request) {
        try {
            recuperarPasswordService.enviarCodigoRecuperacion(request.getCorreo());
            
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Código enviado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Usuario no encontrado");
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/recuperar-password/validar-codigo")
    public ResponseEntity<?> validarCodigo(@RequestBody ValidarCodigoRequest request) {
        boolean valido = recuperarPasswordService.validarCodigo(request.getCorreo(), request.getCodigo());
        
        Map<String, Object> response = new HashMap<>();
        if (valido) {
            response.put("valido", true);
            response.put("mensaje", "Código validado correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("valido", false);
            response.put("error", "Código inválido o expirado");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/recuperar-password/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@RequestBody NuevaPasswordRequest request) {
        try {
            recuperarPasswordService.cambiarPassword(
                request.getCorreo(), 
                request.getCodigo(), 
                request.getNuevaPassword()
            );
            
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Contraseña actualizada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
