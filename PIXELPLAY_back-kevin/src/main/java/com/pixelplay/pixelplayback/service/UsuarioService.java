//UsuarioService.java
package com.pixelplay.pixelplayback.service;

import com.pixelplay.pixelplayback.dto.response.UsuarioDTO;
import com.pixelplay.pixelplayback.entity.Usuario;
import com.pixelplay.pixelplayback.repository.UsuarioRepository;
import org.apache.commons.lang3.StringUtils; // ✅ COMMONS LANG3
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ExcelExportService excelExportService;

    public Page<UsuarioDTO> listarTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return usuarioRepository.findAll(pageable)
                .map(this::convertirADTO);
    }

    public Page<UsuarioDTO> listarActivos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return usuarioRepository.findByActivoTrue(pageable)
                .map(this::convertirADTO);
    }

    public Page<UsuarioDTO> buscar(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return usuarioRepository.buscarUsuarios(keyword, pageable)
                .map(this::convertirADTO);
    }

    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirADTO(usuario);
    }

    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {
        // ✅ USO DE COMMONS LANG3 - Validaciones
        if (StringUtils.isBlank(usuarioDTO.getNombre())) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        
        if (StringUtils.isBlank(usuarioDTO.getCorreo())) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }
        
        // Normalizar datos con Commons Lang3
        Usuario usuario = new Usuario();
        usuario.setNombre(StringUtils.capitalize(usuarioDTO.getNombre().trim().toLowerCase()));
        usuario.setApellido(StringUtils.capitalize(usuarioDTO.getApellido().trim().toLowerCase()));
        usuario.setCorreo(StringUtils.lowerCase(usuarioDTO.getCorreo().trim()));
        usuario.setPassword(passwordEncoder.encode("123456"));
        usuario.setTelefono(StringUtils.trimToEmpty(usuarioDTO.getTelefono()));
        usuario.setDireccion(StringUtils.trimToEmpty(usuarioDTO.getDireccion()));
        usuario.setActivo(true);
        
        Usuario guardado = usuarioRepository.save(usuario);
        return convertirADTO(guardado);
    }

    public UsuarioDTO actualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // ✅ USO DE COMMONS LANG3
        if (StringUtils.isNotBlank(usuarioDTO.getNombre())) {
            usuario.setNombre(StringUtils.capitalize(usuarioDTO.getNombre().trim().toLowerCase()));
        }
        
        if (StringUtils.isNotBlank(usuarioDTO.getApellido())) {
            usuario.setApellido(StringUtils.capitalize(usuarioDTO.getApellido().trim().toLowerCase()));
        }
        
        if (StringUtils.isNotBlank(usuarioDTO.getCorreo())) {
            usuario.setCorreo(StringUtils.lowerCase(usuarioDTO.getCorreo().trim()));
        }
        
        usuario.setTelefono(StringUtils.trimToEmpty(usuarioDTO.getTelefono()));
        usuario.setDireccion(StringUtils.trimToEmpty(usuarioDTO.getDireccion()));
        
        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirADTO(actualizado);
    }

    public void eliminar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
    
    // ✅ MÉTODO PARA EXPORTAR A EXCEL (POI)
    public byte[] exportarUsuariosExcel() {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            return excelExportService.exportarUsuariosAExcel(usuarios);
        } catch (Exception e) {
            throw new RuntimeException("Error al exportar usuarios a Excel", e);
        }
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setTelefono(usuario.getTelefono());
        dto.setDireccion(usuario.getDireccion());
        
        if (!usuario.getRoles().isEmpty()) {
            dto.setRol(usuario.getRoles().iterator().next().getNombre());
        } else {
            dto.setRol("ROLE_USER");
        }
        
        dto.setActivo(usuario.getActivo());
        return dto;
    }
}
