package com.pixelplay.pixelplayback;

import com.pixelplay.pixelplayback.cronjob.DescuentoStockJob; // Importar el Job de Descuentos
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner; // Necesario para ejecutar código al inicio
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
// 1. Implementar CommandLineRunner para ejecutar código en el método run()
public class PixelplaybackApplication implements CommandLineRunner {

    // 2. Inyectar el Job de Descuentos
    @Autowired
    private DescuentoStockJob descuentoStockJob; 

    // Opcional: Si el Job de Población está en otro archivo, inyéctalo aquí también
    // @Autowired
    // private PoblacionUsuarioJob poblacionUsuarioJob;

    public static void main(String[] args) {
        SpringApplication.run(PixelplaybackApplication.class, args);
    }

    // 3. Ejecutar el Job de Descuentos inmediatamente al inicio
    @Override
    public void run(String... args) throws Exception {
        
        // Ejecución forzada del Job de Descuentos para generar el PDF.
        // Esto reemplaza la necesidad de usar @Scheduled para las pruebas.
        System.out.println("=============================================");
        System.out.println("🚨 EJECUTANDO DESCUENTO STOCK Y PDF (FORZADO AL INICIO) 🚨");
        System.out.println("=============================================");
        descuentoStockJob.aplicarDescuentosPorStock(); 

        // Aquí iría el Job de Población si se ejecuta al inicio, por ejemplo:
        // poblacionUsuarioJob.poblarUsuarios(); 
    }
}