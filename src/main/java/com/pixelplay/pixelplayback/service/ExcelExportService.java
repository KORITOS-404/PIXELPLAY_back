package com.pixelplay.pixelplayback.service;

import com.pixelplay.pixelplayback.entity.Usuario;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    public byte[] exportarUsuariosAExcel(List<Usuario> usuarios) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");

        // Estilo para el encabezado
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // Crear encabezado
        Row headerRow = sheet.createRow(0);
        String[] columnas = {"ID", "Nombre", "Apellido", "Correo", "Teléfono", "Dirección", "Rol", "Activo"};
        
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 4000);
        }

        // Llenar datos
        int rowNum = 1;
        for (Usuario usuario : usuarios) {
            Row row = sheet.createRow(rowNum++);
            
            row.createCell(0).setCellValue(usuario.getIdUsuario());
            row.createCell(1).setCellValue(usuario.getNombre());
            row.createCell(2).setCellValue(usuario.getApellido());
            row.createCell(3).setCellValue(usuario.getCorreo());
            row.createCell(4).setCellValue(usuario.getTelefono() != null ? usuario.getTelefono() : "");
            row.createCell(5).setCellValue(usuario.getDireccion() != null ? usuario.getDireccion() : "");
            
            String rol = usuario.getRoles().isEmpty() ? "SIN ROL" : 
                         usuario.getRoles().iterator().next().getNombre();
            row.createCell(6).setCellValue(rol);
            row.createCell(7).setCellValue(usuario.getActivo() ? "Sí" : "No");
        }

        // Convertir a bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        
        return outputStream.toByteArray();
    }
}
