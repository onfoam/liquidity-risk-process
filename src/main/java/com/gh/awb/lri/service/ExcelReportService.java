package com.gh.awb.lri.service;

import com.gh.awb.lri.entity.LiquidityRiskReport;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Single source of truth for Excel report generation.
 * Used by both ReportGenerationHandler (BPMN) and LiquidityRiskController (REST download).
 */
public class ExcelReportService {

    public byte[] generate(LiquidityRiskReport r) throws IOException {
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            Sheet sh = wb.createSheet("Liquidity Risk Indicators");

            CellStyle hdrStyle = wb.createCellStyle();
            Font hdrFont = wb.createFont();
            hdrFont.setBold(true);
            hdrFont.setFontHeightInPoints((short) 12);
            hdrStyle.setFont(hdrFont);

            CellStyle pctStyle = wb.createCellStyle();
            pctStyle.setDataFormat(wb.createDataFormat().getFormat("0.00%"));

            // Title block
            Row titleRow = sh.createRow(0);
            titleRow.createCell(0).setCellValue("Liquidity Risk Indicators \u2014 Al-Wahda Bank");
            titleRow.getCell(0).setCellStyle(hdrStyle);

            sh.createRow(1).createCell(0).setCellValue(
                "Year: " + r.getReportYear() +
                " | Quarter: " + r.getQuarterRange() +
                " | Version: " + r.getVersionNumber());
            sh.createRow(2).createCell(0).setCellValue("Status: " + r.getStatus());

            // Table header
            Row hdr = sh.createRow(4);
            String[] cols = {"Financial Indicator", "Q(n) Result", "Q(n+1) Result", "Absolute Coverage"};
            for (int i = 0; i < cols.length; i++) {
                Cell c = hdr.createCell(i);
                c.setCellValue(cols[i]);
                c.setCellStyle(hdrStyle);
            }

            // Data
            Object[][] data = {
                {"Liquidity Coverage Ratio (LCR)", r.getLcrQA(), r.getLcrQB(), r.getLcrCoverage()},
                {"Net Stable Funding Ratio (NSFR)", r.getNsfrQA(), r.getNsfrQB(), r.getNsfrCoverage()},
                {"Leverage Ratio", r.getLeverageQA(), r.getLeverageQB(), r.getLeverageCoverage()}
            };
            for (int i = 0; i < data.length; i++) {
                Row row = sh.createRow(5 + i);
                row.createCell(0).setCellValue((String) data[i][0]);
                for (int j = 1; j <= 3; j++) {
                    Cell c = row.createCell(j);
                    if (data[i][j] instanceof Double) {
                        c.setCellValue((Double) data[i][j]);
                        c.setCellStyle(pctStyle);
                    } else {
                        c.setCellValue("N/A");
                    }
                }
            }

            // Comments
            int commentRow = 10;
            sh.createRow(commentRow).createCell(0).setCellValue("Employee Comment:");
            sh.getRow(commentRow).getCell(0).setCellStyle(hdrStyle);
            sh.createRow(commentRow + 1).createCell(0).setCellValue(
                r.getEmployeeComment() != null ? r.getEmployeeComment() : "\u2014");

            sh.createRow(commentRow + 3).createCell(0).setCellValue("Manager Comment:");
            sh.getRow(commentRow + 3).getCell(0).setCellStyle(hdrStyle);
            sh.createRow(commentRow + 4).createCell(0).setCellValue(
                r.getManagerComment() != null ? r.getManagerComment() : "\u2014");

            if (r.getApprovedBy() != null) {
                sh.createRow(commentRow + 6).createCell(0).setCellValue(
                    "Approved by: " + r.getApprovedBy() + " on " + r.getApprovedAt());
            }

            for (int i = 0; i < cols.length; i++) sh.autoSizeColumn(i);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            return bos.toByteArray();
        }
    }

    public String fileName(LiquidityRiskReport r) {
        return String.format("LRI_%d_%s_v%d.xlsx",
            r.getReportYear(), r.getQuarterRange(), r.getVersionNumber());
    }
}
