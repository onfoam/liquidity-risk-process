package com.gh.awb.lri.handler;

import com.gh.awb.lri.entity.LiquidityRiskReport;
import com.gh.awb.lri.entity.ReportStatus;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.manager.RuntimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Archives the final approved report by updating the DB directly via JPA.
 * Uses EntityManagerFactory from the jBPM runtime environment (same as DataValidationHandler),
 * avoiding the CDI/JNDI lookup issue.
 */
public class ArchiveReportHandler implements WorkItemHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ArchiveReportHandler.class);
    private final RuntimeManager runtimeManager;

    public ArchiveReportHandler(RuntimeManager runtimeManager) {
        this.runtimeManager = runtimeManager;
    }

    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.info("ArchiveReport: Starting for work item {}", workItem.getId());
        Map<String, Object> results = new HashMap<>();

        // Get report entity ID from process variable
        Long reportEntityId = ((Number) workItem.getParameter("reportEntityId")).longValue();
        String approvedBy = (String) workItem.getParameter("approvedBy");

        EntityManagerFactory emf = (EntityManagerFactory) runtimeManager
            .getEnvironment().getEnvironment().get("org.kie.api.persistence.EntityManagerFactory");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            LiquidityRiskReport report = em.find(LiquidityRiskReport.class, reportEntityId);
            if (report == null) {
                throw new RuntimeException("Report not found: " + reportEntityId);
            }

            report.setStatus(ReportStatus.ARCHIVED);
            report.setApprovedAt(LocalDateTime.now());
            report.setApprovedBy(approvedBy != null ? approvedBy : "system");
            em.merge(report);

            // Insert audit log directly
            em.createNativeQuery(
                "INSERT INTO lri_audit_log (report_id, action, performed_by, performed_at, comment) " +
                "VALUES (:rid, :act, :by, :at, :cmt)")
                .setParameter("rid", report.getId())
                .setParameter("act", "ARCHIVED")
                .setParameter("by", report.getApprovedBy())
                .setParameter("at", LocalDateTime.now())
                .setParameter("cmt", "Report archived after final approval.")
                .executeUpdate();

            em.getTransaction().commit();

            results.put("reportStatus", "ARCHIVED");
            LOG.info("ArchiveReport: Report #{} archived by {}", reportEntityId, approvedBy);
            manager.completeWorkItem(workItem.getId(), results);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            LOG.error("ArchiveReport: Failed - {}", e.getMessage(), e);
            throw new RuntimeException("Archive failed: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.warn("ArchiveReport: Aborted {}", workItem.getId());
    }
}
