package com.pixelplay.pixelplayback.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;
    
    @Column(nullable = false, unique = true, length = 20)
    private String nombre; // "ROLE_USER", "ROLE_ADMIN"
}
