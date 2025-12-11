//  MetodoPago.java
package com.pixelplay.pixelplayback.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "metodos_pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetodoPago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo_pago")
    private Long idMetodoPago;
    
    @Column(nullable = false, length = 20)
    private String tipo; // "Tarjeta", "PayPal", "Transferencia", "Efectivo"
    
    @Column(name = "numero_tarjeta", length = 16)
    private String numeroTarjeta;
    
    @Column(name = "nombre_titular", length = 100)
    private String nombreTitular;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    @Column(nullable = false)
    private Boolean activo = true;
}
