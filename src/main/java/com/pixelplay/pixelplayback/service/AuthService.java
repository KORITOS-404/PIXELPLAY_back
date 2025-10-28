package com.pixelplay.pixelplayback.service;

import com.pixelplay.pixelplayback.dto.request.LoginRequest;
import com.pixelplay.pixelplayback.dto.request.RegisterRequest;
import com.pixelplay.pixelplayback.dto.response.AuthResponse;
import com.pixelplay.pixelplayback.entity.Role;
import com.pixelplay.pixelplayback.entity.Usuario;
import com.pixelplay.pixelplayback.repository.RoleRepository;
import com.pixelplay.pixelplayback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Verificar si el correo ya existe
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        
        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setDireccion(request.getDireccion());
        usuario.setTelefono(request.getTelefono());
        
        // Asignar rol USER por defecto
        Role userRole = roleRepository.findByNombre("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setNombre("ROLE_USER");
                    return roleRepository.save(newRole);
                });
        
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        usuario.setRoles(roles);
        
        // Guardar usuario
        usuarioRepository.save(usuario);
        
        // Generar token JWT
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getCorreo());
        String token = jwtService.generateToken(userDetails);
        
        return new AuthResponse(
                token,
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                "ROLE_USER"
        );
    }
    
    public AuthResponse login(LoginRequest request) {
        // Autenticar usuario
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getPassword()
                )
        );
        
        // Buscar usuario
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Generar token JWT
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getCorreo());
        String token = jwtService.generateToken(userDetails);
        
        // Obtener el primer rol (normalmente solo tiene uno)
        String rol = usuario.getRoles().isEmpty() ? "ROLE_USER" : 
                     usuario.getRoles().iterator().next().getNombre();
        
        return new AuthResponse(
                token,
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                rol
        );
    }
}
