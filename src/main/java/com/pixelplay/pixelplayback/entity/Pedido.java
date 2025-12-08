package com.pixelplay.pixelplayback.entity;

import com.pixelplay.pixelplayback.enums.EstadoPedido;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;
    
    @Column(name = "numero_pedido", unique = true, nullable = false)
    private String numeroPedido;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties({"pedidos", "password", "hibernateLazyInitializer", "handler"})
    private Usuario usuario;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "apellido")
    private String apellido;
    
    @Column(name = "correo")
    private String correo;
    
    @Column(name = "telefono")
    private String telefono;
    
    @Column(name = "direccion")
    private String direccion;
    
    @Column(name = "metado_pago")
    private String metadoPago;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPedido estado;
    
    @Column(name = "monto_total", precision = 10, scale = 2)
    private BigDecimal montoTotal;
    
    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"pedido", "hibernateLazyInitializer", "handler"})
    private List<DetallePedido> detalles = new ArrayList<>();
}
