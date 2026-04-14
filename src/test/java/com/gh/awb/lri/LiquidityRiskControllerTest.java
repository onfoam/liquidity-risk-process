package com.gh.awb.lri;

import com.gh.awb.lri.entity.LiquidityRiskReport;
import com.gh.awb.lri.entity.ReportStatus;
import com.gh.awb.lri.repository.LiquidityRiskAuditLogRepository;
import com.gh.awb.lri.repository.LiquidityRiskReportRepository;
import com.gh.awb.lri.rest.LiquidityRiskController;
import com.gh.awb.lri.service.ExcelReportService;
import com.gh.awb.lri.service.ProcessBridgeService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for LiquidityRiskController.
 * Tests: validation, status codes, error paths, download.
 */
public class LiquidityRiskControllerTest {

    private LiquidityRiskController controller;
    private ProcessBridgeService processService;
    private LiquidityRiskReportRepository reportRepo;
    private LiquidityRiskAuditLogRepository auditRepo;
    private ExcelReportService excelService;

    @Before
    public void setUp() {
        processService = mock(ProcessBridgeService.class);
        reportRepo = mock(LiquidityRiskReportRepository.class);
        auditRepo = mock(LiquidityRiskAuditLogRepository.class);
        excelService = mock(ExcelReportService.class);
        controller = new LiquidityRiskController(processService, reportRepo, auditRepo, excelService);
    }

    @Test
    public void createReport_returnsOk() {
        LiquidityRiskReport mockReport = new LiquidityRiskReport(2025, "Q1-Q2", "maker1");
        when(processService.startProcess(2025, "Q1-Q2", "maker1")).thenReturn(mockReport);

        ResponseEntity<LiquidityRiskReport> response = controller.createReport(2025, "Q1-Q2", "maker1");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getReportYear()).isEqualTo(2025);
        verify(processService).startProcess(2025, "Q1-Q2", "maker1");
    }

    @Test
    public void returnToMaker_emptyComment_returns400() {
        ResponseEntity<?> response = controller.returnToMaker(1L, "", "checker1");

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        @SuppressWarnings("unchecked")
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertThat(body).containsKey("error");
        assertThat(body).containsKey("errorAr");
    }

    @Test
    public void returnToMaker_nullComment_returns400() {
        ResponseEntity<?> response = controller.returnToMaker(1L, null, "checker1");

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void getReport_notFound_returns404() {
        when(reportRepo.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<LiquidityRiskReport> response = controller.getReport(999L);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void getReport_found_returns200() {
        LiquidityRiskReport r = new LiquidityRiskReport(2025, "Q1-Q2", "maker1");
        when(reportRepo.findById(1L)).thenReturn(Optional.of(r));

        ResponseEntity<LiquidityRiskReport> response = controller.getReport(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getQuarterRange()).isEqualTo("Q1-Q2");
    }

    @Test
    public void checkDuplicate_returnsCorrectCounts() {
        when(reportRepo.countByReportYearAndQuarterRangeAndStatus(2025, "Q1-Q2", ReportStatus.ARCHIVED)).thenReturn(2L);
        when(reportRepo.countActiveByPeriod(2025, "Q1-Q2")).thenReturn(1L);

        ResponseEntity<Map<String, Object>> response = controller.checkDuplicate(2025, "Q1-Q2");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().get("archivedExists")).isEqualTo(true);
        assertThat(response.getBody().get("activeExists")).isEqualTo(true);
        assertThat(response.getBody().get("archivedVersions")).isEqualTo(2L);
    }

    @Test
    public void downloadReport_returnsExcel() throws Exception {
        LiquidityRiskReport r = new LiquidityRiskReport(2025, "Q1-Q2", "maker1");
        r.setVersionNumber(1);
        when(reportRepo.findById(1L)).thenReturn(Optional.of(r));
        when(excelService.generate(r)).thenReturn(new byte[]{1, 2, 3});
        when(excelService.fileName(r)).thenReturn("LRI_2025_Q1-Q2_v1.xlsx");

        ResponseEntity<byte[]> response = controller.downloadReport(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getHeaders().getContentDisposition().getFilename()).contains("LRI_2025");
        assertThat(response.getBody()).hasSize(3);
    }
}
