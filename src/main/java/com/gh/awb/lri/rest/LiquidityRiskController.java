package com.gh.awb.lri.rest;

import com.gh.awb.lri.entity.LiquidityRiskAuditLog;
import com.gh.awb.lri.entity.LiquidityRiskReport;
import com.gh.awb.lri.entity.ReportStatus;
import com.gh.awb.lri.repository.LiquidityRiskAuditLogRepository;
import com.gh.awb.lri.repository.LiquidityRiskReportRepository;
import com.gh.awb.lri.security.LriRoles;
import com.gh.awb.lri.service.ExcelReportService;
import com.gh.awb.lri.service.ProcessBridgeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lri")
public class LiquidityRiskController {

    private final ProcessBridgeService processService;
    private final LiquidityRiskReportRepository reportRepo;
    private final LiquidityRiskAuditLogRepository auditRepo;
    private final ExcelReportService excelService;

    public LiquidityRiskController(ProcessBridgeService processService,
                                   LiquidityRiskReportRepository reportRepo,
                                   LiquidityRiskAuditLogRepository auditRepo,
                                   ExcelReportService excelService) {
        this.processService = processService;
        this.reportRepo = reportRepo;
        this.auditRepo = auditRepo;
        this.excelService = excelService;
    }

    // ═══ PROCESS LIFECYCLE ═══

    @PostMapping("/reports")
    @RolesAllowed(LriRoles.MAKER)
    public ResponseEntity<LiquidityRiskReport> createReport(
            @RequestParam Integer year, @RequestParam String quarterRange, @RequestParam String createdBy) {
        return ResponseEntity.ok(processService.startProcess(year, quarterRange, createdBy));
    }

    @PostMapping("/reports/{id}/save")
    @RolesAllowed(LriRoles.MAKER)
    public ResponseEntity<LiquidityRiskReport> saveDraft(
            @PathVariable Long id, @RequestParam(required = false) String employeeComment, @RequestParam String userId) {
        return ResponseEntity.ok(processService.saveDraft(id, employeeComment, userId));
    }

    @PostMapping("/reports/{id}/submit")
    @RolesAllowed(LriRoles.MAKER)
    public ResponseEntity<LiquidityRiskReport> submit(
            @PathVariable Long id, @RequestParam(required = false) String employeeComment, @RequestParam String userId) {
        return ResponseEntity.ok(processService.submitToManager(id, employeeComment, userId));
    }

    @PostMapping("/reports/{id}/approve-manager")
    @RolesAllowed(LriRoles.CHECKER)
    public ResponseEntity<LiquidityRiskReport> managerApprove(
            @PathVariable Long id, @RequestParam(required = false) String managerComment, @RequestParam String userId) {
        return ResponseEntity.ok(processService.managerApprove(id, managerComment, userId));
    }

    @PostMapping("/reports/{id}/return")
    @RolesAllowed(LriRoles.CHECKER)
    public ResponseEntity<?> returnToMaker(
            @PathVariable Long id, @RequestParam String returnComment, @RequestParam String userId) {
        if (returnComment == null || returnComment.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Return comment is mandatory.",
                "errorAr", "التعليق مطلوب عند إعادة التقرير."));
        }
        return ResponseEntity.ok(processService.returnToMaker(id, returnComment, userId));
    }

    @PostMapping("/reports/{id}/approve-director")
    @RolesAllowed(LriRoles.APPROVER)
    public ResponseEntity<LiquidityRiskReport> directorApprove(
            @PathVariable Long id, @RequestParam String userId) {
        return ResponseEntity.ok(processService.directorApprove(id, userId));
    }

    // ═══ QUERIES ═══

    @GetMapping("/reports/{id}")
    @RolesAllowed(LriRoles.ALL)
    public ResponseEntity<LiquidityRiskReport> getReport(@PathVariable Long id) {
        return reportRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reports/by-process/{pid}")
    @RolesAllowed(LriRoles.ALL)
    public ResponseEntity<LiquidityRiskReport> getByProcessId(@PathVariable Long pid) {
        return reportRepo.findByProcessInstanceId(pid).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reports/archive")
    @RolesAllowed(LriRoles.ALL)
    public ResponseEntity<Page<LiquidityRiskReport>> getArchived(Pageable p) {
        return ResponseEntity.ok(reportRepo.findByStatusOrderByApprovedAtDesc(ReportStatus.ARCHIVED, p));
    }

    @GetMapping("/reports/check-duplicate")
    @RolesAllowed(LriRoles.MAKER)
    public ResponseEntity<Map<String, Object>> checkDuplicate(
            @RequestParam Integer year, @RequestParam String quarterRange) {
        long archived = reportRepo.countByReportYearAndQuarterRangeAndStatus(year, quarterRange, ReportStatus.ARCHIVED);
        long active = reportRepo.countActiveByPeriod(year, quarterRange);
        return ResponseEntity.ok(Map.of(
            "archivedExists", archived > 0, "activeExists", active > 0,
            "archivedVersions", archived, "activeVersions", active));
    }

    @GetMapping("/reports/{id}/audit")
    @RolesAllowed({LriRoles.CHECKER, LriRoles.APPROVER})
    public ResponseEntity<List<LiquidityRiskAuditLog>> getAuditTrail(@PathVariable Long id) {
        return ResponseEntity.ok(auditRepo.findByReportIdOrderByPerformedAtAsc(id));
    }

    // ═══ DOWNLOAD (uses shared ExcelReportService) ═══

    @GetMapping("/reports/{id}/download")
    @RolesAllowed({LriRoles.CHECKER, LriRoles.APPROVER})
    public ResponseEntity<byte[]> downloadReport(@PathVariable Long id) {
        LiquidityRiskReport report = reportRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Report not found: " + id));
        try {
            byte[] bytes = excelService.generate(report);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + excelService.fileName(report) + "\"")
                .contentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
