package edu.esprit.controllers;

import edu.esprit.entities.Plat;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

    public class ExcelExporter {

        public static void exportPlatsToExcel(List<Plat> plats, String filePath) throws IOException {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Plats");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = { "Nom", "Prix", "Description", "Allergies", "Etat", "Calories"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Fill data rows
            int rowNum = 1;
            for (Plat plat : plats) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(plat.getNomP());
                row.createCell(1).setCellValue(plat.getPrixP());
                row.createCell(2).setCellValue(plat.getDescP());
                row.createCell(3).setCellValue(plat.getAlergieP());
                row.createCell(4).setCellValue(plat.getEtatP() ? "En stock" : "Rupture stock");
                row.createCell(5).setCellValue(plat.getCalories());
            }


            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }


            workbook.close();
        }
    }


