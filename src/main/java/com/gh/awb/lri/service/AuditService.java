package com.gh.awb.lri.service;

import com.gh.awb.lri.entity.LiquidityRiskAuditLog;
import com.gh.awb.lri.entity.LiquidityRiskReport;
import com.gh.awb.lri.repository.LiquidityRiskAuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Centralized audit logging for all report lifecycle events.
 */
public class AuditService {

    private static final Logger LOG = LoggerFactory.getLogger(AuditService.class);
    private final LiquidityRiskAuditLogRepository auditRepo;

    public AuditService(LiquidityRiskAuditLogRepository auditRepo) {
        this.auditRepo = auditRepo;
    }

    public void log(LiquidityRiskReport report, String action, String performedBy, String comment) {
        LiquidityRiskAuditLog entry = new LiquidityRiskAuditLog(report, action, performedBy, comment);
        auditRepo.save(entry);
        LOG.info("AUDIT: [{}] Report#{} by {} - {}", action, report.getId(), performedBy, comment != null ? comment : "");
    }

    public List<LiquidityRiskAuditLog> getAuditTrail(Long reportId) {
        return auditRepo.findByReportIdOrderByPerformedAtAsc(reportId);
    }
}
