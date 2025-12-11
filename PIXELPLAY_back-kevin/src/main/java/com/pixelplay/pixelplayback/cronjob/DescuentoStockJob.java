//  DescuentoStockJob.java
package com.pixelplay.pixelplayback.cronjob;

import com.pixelplay.pixelplayback.entity.Producto;
import com.pixelplay.pixelplayback.repository.ProductoRepository;
import com.pixelplay.pixelplayback.service.DescuentoPdfService;
import org.springframework.beans.factory.annotation.Autowired;
// 🎯 CAMBIADO: Usando fixedRate para la prueba inmediata
import org.springframework.scheduling.annotation.Scheduled; 
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList; 
import java.util.List;

@Component
public class DescuentoStockJob {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DescuentoPdfService descuentoPdfService;

    // Constantes del Job
    private static final int LIMITE_STOCK = 70;
    private static final BigDecimal PORCENTAJE_DESCUENTO = new BigDecimal("0.10");

    // @Scheduled(cron = "0 0 0 * * *") // Configuración original de medianoche
   // @Scheduled(fixedRate = 30000) // 🎯 Configuración para prueba inmediata
    @Transactional
    public void aplicarDescuentosPorStock() {
        System.out.println("\n=============================================");
        System.out.println("🔥 CRON JOB: Iniciando gestión de descuentos por bajo stock.");
        System.out.println("=============================================");

        List<Producto> productosDescontados = new ArrayList<>(); 

        try {
            // 1. Consultar la BD: Buscar todos los juegos cuyo stock sea <= 70
            List<Producto> productosBajoStock = productoRepository.findByStockLessThanEqual(LIMITE_STOCK);

            if (productosBajoStock.isEmpty()) {
                System.out.println("✅ CRON: No se encontraron productos con stock inferior o igual a " + LIMITE_STOCK + ".");
                return;
            }

            int productosActualizados = 0;

            for (Producto producto : productosBajoStock) {
                BigDecimal precioOriginal = producto.getPrecio();

                // 2. Calcular el descuento: precioOriginal * 0.10
                BigDecimal descuento = precioOriginal.multiply(PORCENTAJE_DESCUENTO);

                // 3. Calcular el nuevo precio
                BigDecimal nuevoPrecio = precioOriginal.subtract(descuento)
                                                     .setScale(2, RoundingMode.HALF_UP);
                
                // 4. Aplicar descuento y actualizar registro
                producto.setPrecio(nuevoPrecio);
                productoRepository.save(producto);
                productosActualizados++;

                // 5. Agregar el producto a la lista para el PDF
                productosDescontados.add(producto);

                // 6. Mensaje de Log
                System.out.println(String.format("🔥 CRON: Aplicado descuento del 10%% a '%s'. Precio Original: %s. Nuevo Precio: %s (Stock: %d)",
                        producto.getNombre(), precioOriginal.toString(), nuevoPrecio.toString(), producto.getStock()));
            }

            System.out.println("\n✅ CRON JOB FINALIZADO: " + productosActualizados + " productos actualizados con descuento.");
            
            // 7. Generar PDF si hubo productos descontados
            if (!productosDescontados.isEmpty()) {
                descuentoPdfService.generarPdf(productosDescontados); 
            }

        } catch (Exception e) {
            System.err.println("❌ ERROR grave en el Job de Descuentos o PDF: " + e.getMessage());
        }
    }
}