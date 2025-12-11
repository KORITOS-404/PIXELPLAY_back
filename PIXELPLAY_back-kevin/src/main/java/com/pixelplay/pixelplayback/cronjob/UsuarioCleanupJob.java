//  UsuarioCleanupJob.java
package com.pixelplay.pixelplayback.cronjob; import java.util.Random;
 import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
 import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Transactional;

import com.pixelplay.pixelplayback.entity.Role;
 import com.pixelplay.pixelplayback.entity.Usuario;
import com.pixelplay.pixelplayback.repository.RoleRepository;
 import com.pixelplay.pixelplayback.repository.UsuarioRepository;

@Component
public class UsuarioCleanupJob {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository; // Necesario para asignar roles

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Contador atómico para rastrear cuántos usuarios hemos creado en este job
    private final AtomicInteger usuariosCreados = new AtomicInteger(0);
    private static final int LIMITE_USUARIOS = 20;

    // Listas de datos aleatorios (para simulación)
    private static final String[] NOMBRES = {"Alejandro", "Sofia", "Kevin", "Daniela", "Luis", "Andrea", "Carlos", "Fernanda"};
    private static final String[] APELLIDOS = {"Lopez", "Garcia", "Perez", "Rodriguez", "Sanchez", "Ramirez", "Torres", "Flores"};
    private static final String[] DOMINIOS = {"@gmail.com", "@hotmail.com", "@pixelplay.com"};
    private static final String[] DIRECCIONES = {"Av. Los Girasoles 123", "Calle Las Margaritas 456", "Jr. Huancayo 789", "Pje. El Sol 101"};

    // 🎯 Se ejecuta cada 2 segundos (2000 ms)
    @Scheduled(fixedRate = 2000)
    @Transactional
    public void crearUsuariosAleatorios() {
        if (usuariosCreados.get() >= LIMITE_USUARIOS) {
            // Detener la creación después de alcanzar el límite
            System.out.println("✅ CRON JOB DE POBLACIÓN DETENIDO: Límite de " + LIMITE_USUARIOS + " usuarios alcanzado.");
            return;
        }

        if (usuarioRepository.count() >= LIMITE_USUARIOS) {
             System.out.println("✅ CRON JOB DE POBLACIÓN DETENIDO: La tabla ya tiene " + LIMITE_USUARIOS + " usuarios.");
             usuariosCreados.set(LIMITE_USUARIOS); // Actualizar contador interno
             return;
        }

        try {
            Usuario nuevoUsuario = generarUsuarioDePrueba();
            usuarioRepository.save(nuevoUsuario);
            usuariosCreados.incrementAndGet();

            System.out.println(String.format("🤖 CRON JOB - CREADO #%d: %s | Correo: %s",
                    usuariosCreados.get(), nuevoUsuario.getNombre() + " " + nuevoUsuario.getApellido(), nuevoUsuario.getCorreo()));

        } catch (Exception e) {
            System.err.println("❌ ERROR en el Cron Job de creación de usuario: " + e.getMessage());
        }
    }

    private Usuario generarUsuarioDePrueba() {
        Random rand = new Random();
        
        // 1. Nombres y Apellidos
        String nombre = NOMBRES[rand.nextInt(NOMBRES.length)];
        String apellido1 = APELLIDOS[rand.nextInt(APELLIDOS.length)];
        String apellido2 = APELLIDOS[rand.nextInt(APELLIDOS.length)];
        String apellidoCompleto = apellido1 + " " + apellido2;

        // 2. Correo aleatorio
        String parteCorreo = apellido1 + apellido2.substring(0, 3) + rand.nextInt(100);
        String dominio = DOMINIOS[rand.nextInt(DOMINIOS.length)];
        String correo = parteCorreo.toLowerCase() + dominio;

        // 3. ID de Usuario (DNI de 8 números) - Se mantiene la generación por si se usa en otro lugar, pero se omite en la dirección.
        long dni = (long) (10000000 + rand.nextInt(90000000)); 

        // 4. Teléfono (9 dígitos, empieza con 9)
        long telefono = (long) (900000000L + rand.nextInt(100000000));
        
        // 5. Dirección aleatoria
        String direccionAleatoria = DIRECCIONES[rand.nextInt(DIRECCIONES.length)];

        // 6. Creación de la Entidad
        Usuario usuario = new Usuario();
        // NOTA: El campo 'id_usuario' de la tabla debe ser autogenerado por la BD (Long)
        usuario.setNombre(nombre);
        usuario.setApellido(apellidoCompleto);
        usuario.setCorreo(correo);
        
        // 🚨 MODIFICACIÓN AQUÍ: Solo se asigna la dirección. Se eliminó la concatenación del DNI.
        usuario.setDireccion(direccionAleatoria);
        
        usuario.setTelefono(String.valueOf(telefono)); 
        
        // Contraseña encriptada (usando 'password' como ejemplo)
        usuario.setPassword(passwordEncoder.encode("password_default")); 
        
        // Activo (0 o 1)
        usuario.setActivo(rand.nextBoolean());

        // 7. Asignar Role (es crucial en una aplicación de seguridad)
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    // Crear el rol si no existe (solo para fines de prueba)
                    Role newRole = new Role();
                    newRole.setNombre("ROLE_USER");
                    return roleRepository.save(newRole);
                });

        usuario.setRoles(Set.of(roleUser));
        
        return usuario;
    }
}