package com.pixelplay.pixelplayback.entity;

import com.pixelplay.pixelplayback.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "id_metodo_pago")
    private MetodoPago metodoPago;
    
    @Column(name = "fecha_pedido", nullable = false)
    private LocalDate fechaPedido;
    
    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;
    
    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;
    
    @Column(name = "documento_fiscal")
    private Boolean documentoFiscal = false;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPedido estado = EstadoPedido.PENDIENTE;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DetallePedido> detalles = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        fechaPedido = LocalDate.now();
        if (estado == null) {
            estado = EstadoPedido.PENDIENTE;
        }
    }
}
