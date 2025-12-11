//  DescuentoPdfService.java
package com.pixelplay.pixelplayback.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pixelplay.pixelplayback.entity.Producto;

@Service
public class DescuentoPdfService {

    private static final String FILE_NAME = "ReporteDescuentos_BajoStock.pdf";
    private static final String FILE_PATH = "./" + FILE_NAME;

    // Fuentes para el diseño (iText 5)
    private static final Font TITULO_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.RED);
    private static final Font LEYENDA_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
    private static final Font ENCABEZADO_TABLA_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);

    public void generarPdf(List<Producto> productosDescontados) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate()); // A4 horizontal

        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE_PATH));
            document.open();

            // 1. Título y Leyenda
            agregarEncabezado(document);

            // 2. Tabla de Descuentos
            agregarTablaDescuentos(document, productosDescontados);

            document.close();
            System.out.println("✅ PDF GENERADO: " + FILE_PATH);

        } catch (DocumentException e) {
            System.err.println("Error al manejar el documento PDF (iText 5): " + e.getMessage());
            throw e;
        }
    }

    private void agregarEncabezado(Document document) throws DocumentException {
        // Título principal (Encabezado Distinguido - ROJO)
        Paragraph titulo = new Paragraph("INFORME DE PRODUCTOS CON DESCUENTO POR BAJO STOCK", TITULO_FONT);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(5);
        document.add(titulo);

        // Leyenda (Simple y observadora - Fondo AMARILLO)
        Paragraph leyenda = new Paragraph("ATENCIÓN: Se requiere generar orden de compra/re-stock para estos productos.", LEYENDA_FONT);
        leyenda.setAlignment(Element.ALIGN_CENTER);
        
        // Usar una tabla de 1 celda para aplicar el fondo AMARILLO de manera uniforme
        PdfPTable tablaLeyenda = new PdfPTable(1);
        tablaLeyenda.setWidthPercentage(100);
        
        PdfPCell cellLeyenda = new PdfPCell(leyenda);
        cellLeyenda.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellLeyenda.setBackgroundColor(BaseColor.YELLOW);
        cellLeyenda.setBorder(Rectangle.NO_BORDER);
        tablaLeyenda.addCell(cellLeyenda);
        
        document.add(tablaLeyenda);
        document.add(new Paragraph(" ")); // Espacio
    }

    private void agregarTablaDescuentos(Document document, List<Producto> productosDescontados) throws DocumentException {
        // 7 Columnas: ID | Nombre | Plataforma | Stock | Precio Original | Nuevo Precio | Descuento Aplicado
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1f, 4f, 2f, 1f, 2f, 2f, 2f}); // Proporciones de las columnas

        // Estilo de celda de encabezado (Fondo Gris Oscuro, Texto Blanco)
        PdfPCell headerStyle = new PdfPCell();
        headerStyle.setBackgroundColor(BaseColor.DARK_GRAY);
        headerStyle.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerStyle.setPadding(5);

        // Encabezados
        table.addCell(createHeaderCell("ID", headerStyle));
        table.addCell(createHeaderCell("NOMBRE", headerStyle));
        table.addCell(createHeaderCell("PLATAFORMA", headerStyle));
        table.addCell(createHeaderCell("STOCK", headerStyle));
        table.addCell(createHeaderCell("PRECIO ORIGINAL", headerStyle));
        table.addCell(createHeaderCell("NUEVO PRECIO", headerStyle));
        table.addCell(createHeaderCell("DESCUENTO (%)", headerStyle));

        // Datos de los Productos
        for (Producto p : productosDescontados) {
            // Calcular el precio original (Inversa del 10% de descuento)
            double nuevoPrecio = p.getPrecio().doubleValue();
            double original = nuevoPrecio / 0.90; // nuevoPrecio / (1.0 - 0.10)
            
            // Redondear el original para mostrar
            original = new java.math.BigDecimal(original).setScale(2, RoundingMode.HALF_UP).doubleValue();

            table.addCell(p.getIdProducto().toString());
            table.addCell(p.getNombre());
            table.addCell(p.getPlataforma());
            table.addCell(p.getStock().toString());
            table.addCell("$" + String.format("%.2f", original));
            table.addCell("$" + p.getPrecio().toString());
            table.addCell("10%");
        }

        document.add(table);
    }
    
    private PdfPCell createHeaderCell(String text, PdfPCell baseStyle) {
        PdfPCell cell = new PdfPCell(baseStyle);
        cell.setPhrase(new Phrase(text, ENCABEZADO_TABLA_FONT));
        return cell;
    }
}