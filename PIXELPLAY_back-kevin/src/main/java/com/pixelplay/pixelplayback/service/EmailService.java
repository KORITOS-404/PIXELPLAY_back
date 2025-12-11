//EmailService.java
package com.pixelplay.pixelplayback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCodigoRecuperacion(String destinatario, String codigo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Código de Recuperación - PixelPlay");
        mensaje.setText(
            "Hola,\n\n" +
            "Tu código de recuperación es: " + codigo + "\n\n" +
            "Este código expira en 10 minutos.\n\n" +
            "Si no solicitaste este código, ignora este mensaje.\n\n" +
            "Saludos,\n" +
            "Equipo PixelPlay"
        );
        
        mailSender.send(mensaje);
    }
}
