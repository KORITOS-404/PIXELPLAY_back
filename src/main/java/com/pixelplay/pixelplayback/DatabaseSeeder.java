// DatabaseSeeder.java

package com.pixelplay.pixelplayback;

// ✅ CORREGIDO: Usar 'entity' en lugar de 'models'
import com.pixelplay.pixelplayback.entity.Usuario; 

// ✅ CORREGIDO: Usar 'repository' en lugar de 'repositories'
import com.pixelplay.pixelplayback.repository.UsuarioRepository; 

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseSeeder {

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            
            // Verifica si ya existe el usuario para no duplicarlo
            if (usuarioRepository.findByCorreo("admin@pixelplay.com").isEmpty()) { 
                
                // 1. Crear el usuario
                Usuario admin = new Usuario();
                admin.setNombre("Roberto");
                admin.setApellido("Admin");
                admin.setCorreo("admin@pixelplay.com");
                
                // 2. HASHEAR/ENCRIPTAR la contraseña antes de guardar
                // La contraseña es 'mipasswordseguro'
                admin.setPassword(passwordEncoder.encode("mipasswordseguro")); 
                
                admin.setDireccion("Lima");
                admin.setTelefono("987654321");

                // 3. Opcional: Asignar roles (si tienes lógica de roles)
                // Esto es crucial para la seguridad, pero se omite aquí si aún no tienes el modelo Role
                // Si tienes un RoleRepository, debes agregarlo e inyectarlo aquí.
    
                // 4. Guardar en la base de datos
                usuarioRepository.save(admin);
                System.out.println("ADMINISTRADOR CREADO: admin@pixelplay.com");
            }
        };
    }
}