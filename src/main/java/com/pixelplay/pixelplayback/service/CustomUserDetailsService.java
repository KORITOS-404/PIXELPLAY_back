package com.pixelplay.pixelplayback.service;

import com.pixelplay.pixelplayback.entity.Usuario;
import com.pixelplay.pixelplayback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UsuarioRepository usuarioRepository;
    
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correo));
        
        Set<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toSet());
        
        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(),
                usuario.getPassword(),
                authorities
        );
    }
}
