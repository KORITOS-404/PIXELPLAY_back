package com.pixelplay.pixelplayback.cronjob;

import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pixelplay.pixelplayback.entity.Usuario;
import com.pixelplay.pixelplayback.repository.UsuarioRepository;

@Component
public class GeneradorPdfJob implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String FILE_PATH = "InformeUsuariosPixelPlay.pdf";

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Ejecutar solo si el argumento "generar-pdf" está presente
        if (args.length > 0 && "generar-pdf".equalsIgnoreCase(args[0])) {
            System.out.println("\n=============================================");
            System.out.println("🤖 EJECUTANDO GENERADOR DE INFORME PDF...");
            System.out.println("=============================================\n");
            generarInformePdf();
            System.exit(0); // Detiene la aplicación después de generar el informe
        }
    }

    public void generarInformePdf() {
        Document document = new Document(PageSize.A4.rotate()); // Página horizontal para más espacio

        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE_PATH));
            document.open();

            // 1. ENCABEZADO (Atractivo)
            agregarEncabezado(document);

            // 2. TABLA DE DATOS
            agregarTablaUsuarios(document);

            document.close();
            System.out.println("✅ INFORME GENERADO CON ÉXITO: " + FILE_PATH);

        } catch (Exception e) {
            System.err.println("❌ ERROR al generar el PDF: " + e.getMessage());
        }
    }

    private void agregarEncabezado(Document document) throws DocumentException {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.RED);
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.DARK_GRAY);

        // Título Principal
        Paragraph title = new Paragraph("PIXELPLAY - INFORME DE USUARIOS", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        // Subtítulo
        Paragraph subtitle = new Paragraph("Detalle Completo de Cuentas Activas e Inactivas", subtitleFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(20);
        document.add(subtitle);
    }

    private void agregarTablaUsuarios(Document document) throws DocumentException {
        List<Usuario> usuarios = usuarioRepository.findAll();
        
        // Tabla con 6 columnas
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        float[] columnWidths = {1.5f, 2f, 2f, 3f, 2.5f, 1.5f}; // Anchos relativos
        table.setWidths(columnWidths);

        // Estilos para la cabecera
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
        BaseColor headerColor = new BaseColor(50, 50, 150); // Azul oscuro

        // Agregar cabeceras
        agregarCeldaCabecera(table, "ESTADO", headerFont, headerColor);
        agregarCeldaCabecera(table, "APELLIDO", headerFont, headerColor);
        agregarCeldaCabecera(table, "NOMBRE", headerFont, headerColor);
        agregarCeldaCabecera(table, "CORREO", headerFont, headerColor);
        agregarCeldaCabecera(table, "TELÉFONO", headerFont, headerColor);
        agregarCeldaCabecera(table, "DIRECCIÓN", headerFont, headerColor);

        // Agregar filas de datos
        for (Usuario usuario : usuarios) {
            
            // Columna ESTADO (con lógica 0=INACTIVO, 1=ACTIVO)
            String estadoTexto = usuario.getActivo() ? "ACTIVO" : "INACTIVO";
            BaseColor estadoColor = usuario.getActivo() ? new BaseColor(152, 251, 152) : new BaseColor(255, 160, 122); // Verde claro o salmón
            
            agregarCeldaDato(table, estadoTexto, estadoColor);
            
            // Otras columnas
            agregarCeldaDato(table, usuario.getApellido(), BaseColor.WHITE);
            agregarCeldaDato(table, usuario.getNombre(), BaseColor.WHITE);
            agregarCeldaDato(table, usuario.getCorreo(), BaseColor.WHITE);
            agregarCeldaDato(table, usuario.getTelefono(), BaseColor.WHITE);
            agregarCeldaDato(table, usuario.getDireccion(), BaseColor.WHITE);
        }

        document.add(table);
    }

    private void agregarCeldaCabecera(PdfPTable table, String texto, Font font, BaseColor color) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(color);
        cell.setPadding(5);
        table.addCell(cell);
    }
    
    private void agregarCeldaDato(PdfPTable table, String texto, BaseColor color) {
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase(texto, dataFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(color);
        cell.setPadding(4);
        table.addCell(cell);
    }
}