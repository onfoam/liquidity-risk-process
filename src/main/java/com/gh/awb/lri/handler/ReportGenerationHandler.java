package com.gh.awb.lri.handler;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.manager.RuntimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates a downloadable Excel report with liquidity indicators and comments.
 *
 * Input:  lcrQA, lcrQB, lcrCoverage, nsfrQA, nsfrQB, nsfrCoverage,
 *         leverageQA, leverageQB, leverageCoverage,
 *         employeeComment, managerComment, selectedYear, selectedQuarterRange
 * Output: reportBytes (byte[]), reportFileName (String)
 */
public class ReportGenerationHandler implements WorkItemHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ReportGenerationHandler.class);
    private final RuntimeManager runtimeManager;

    public ReportGenerationHandler(RuntimeManager runtimeManager) {
        this.runtimeManager = runtimeManager;
    }

    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.info("GenerateReport: Starting for work item {}", workItem.getId());

        Map<String, Object> params = workItem.getParameters();
        Map<String, Object> results = new HashMap<>();

        try {
            byte[] reportBytes = generateExcelReport(params);
            String fileName = String.format("LRI_Report_%s_%s.xlsx",
                params.get("selectedYear"), params.get("selectedQuarterRange"));

            results.put("reportBytes", reportBytes);
            results.put("reportFileName", fileName);

            LOG.info("GenerateReport: Generated {} ({} bytes)", fileName, reportBytes.length);
            manager.completeWorkItem(workItem.getId(), results);

        } catch (Exception e) {
            LOG.error("GenerateReport: Failed - {}", e.getMessage(), e);
            manager.abortWorkItem(workItem.getId());
        }
    }

    private byte[] generateExcelReport(Map<String, Object> params) throws Exception {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Liquidity Risk Indicators");

            // Header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);

            // Title
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Liquidity Risk Indicators Report");
            titleCell.setCellStyle(headerStyle);

            // Period info
            Row periodRow = sheet.createRow(1);
            periodRow.createCell(0).setCellValue("Year: " + params.get("selectedYear"));
            periodRow.createCell(1).setCellValue("Quarter Range: " + params.get("selectedQuarterRange"));

            // Table header
            Row headerRow = sheet.createRow(3);
            String[] headers = {"Financial Indicator", "Q(n) Result", "Q(n+1) Result", "Absolute Coverage"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            Object[][] data = {
                {"Liquidity Coverage Ratio (LCR)", params.get("lcrQA"), params.get("lcrQB"), params.get("lcrCoverage")},
                {"Net Stable Funding Ratio (NSFR)", params.get("nsfrQA"), params.get("nsfrQB"), params.get("nsfrCoverage")},
                {"Leverage Ratio", params.get("leverageQA"), params.get("leverageQB"), params.get("leverageCoverage")}
            };

            CellStyle percentStyle = workbook.createCellStyle();
            percentStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));

            for (int i = 0; i < data.length; i++) {
                Row row = sheet.createRow(4 + i);
                row.createCell(0).setCellValue((String) data[i][0]);
                for (int j = 1; j <= 3; j++) {
                    Cell cell = row.createCell(j);
                    if (data[i][j] instanceof Double) {
                        cell.setCellValue((Double) data[i][j]);
                        cell.setCellStyle(percentStyle);
                    } else {
                        cell.setCellValue("N/A");
                    }
                }
            }

            // Comments section
            Row commentHeader = sheet.createRow(9);
            commentHeader.createCell(0).setCellValue("Comments");
            commentHeader.getCell(0).setCellStyle(headerStyle);

            Row empComment = sheet.createRow(10);
            empComment.createCell(0).setCellValue("Employee:");
            empComment.createCell(1).setCellValue(
                params.get("employeeComment") != null ? params.get("employeeComment").toString() : "");

            Row mgrComment = sheet.createRow(11);
            mgrComment.createCell(0).setCellValue("Manager:");
            mgrComment.createCell(1).setCellValue(
                params.get("managerComment") != null ? params.get("managerComment").toString() : "");

            // Auto-size columns
            for (int i = 0; i < 4; i++) sheet.autoSizeColumn(i);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            return bos.toByteArray();
        }
    }

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.warn("GenerateReport: Aborted {}", workItem.getId());
    }
}
