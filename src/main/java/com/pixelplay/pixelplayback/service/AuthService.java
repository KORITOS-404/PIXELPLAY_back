package com.pixelplay.pixelplayback.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pixelplay.pixelplayback.dto.request.LoginRequest;
import com.pixelplay.pixelplayback.dto.request.RegisterRequest;
import com.pixelplay.pixelplayback.dto.response.AuthResponse;
import com.pixelplay.pixelplayback.entity.Role;
import com.pixelplay.pixelplayback.entity.Usuario;
import com.pixelplay.pixelplayback.repository.RoleRepository; // Importación necesaria para el login optimizado
import com.pixelplay.pixelplayback.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

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
        // Autenticar usuario: Si falla, lanza una excepción (ej. BadCredentialsException)
        // y el AuthController devuelve 401. Si es exitoso, devuelve un objeto Authentication.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getPassword()
                )
        );
        
        // Buscar usuario: Ya que la autenticación fue exitosa, el usuario existe.
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Error: Usuario autenticado no encontrado en DB"));
        
        // Generar token JWT: Usamos el UserDetails del Authentication object
        // Aunque usar userDetailsService.loadUserByUsername() también funciona
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
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