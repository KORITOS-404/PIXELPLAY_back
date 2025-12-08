package com.pixelplay.pixelplayback.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    
    @Column(nullable = false, length = 30)
    private String nombre;
    
    @Column(nullable = false, length = 30)
    private String apellido;
    
    @Column(nullable = false, unique = true, length = 100)
    private String correo;
    
    @Column(nullable = false)
    private String password;
    
    @Column(length = 100)
    private String direccion;
    
    @Column(length = 20)
    private String telefono;
    
    @Column(nullable = false)
    private Boolean activo = true;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<Role> roles = new HashSet<>();
    
    // ✅ AGREGADO: Relación con Pedidos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"usuario", "hibernateLazyInitializer", "handler"})
    private List<Pedido> pedidos = new ArrayList<>();
}
