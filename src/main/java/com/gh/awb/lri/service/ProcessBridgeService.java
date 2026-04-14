package com.gh.awb.lri.service;

import com.gh.awb.lri.entity.LiquidityRiskReport;
import com.gh.awb.lri.entity.ReportStatus;
import com.gh.awb.lri.repository.LiquidityRiskReportRepository;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bridges REST API to jBPM workflow engine.
 *
 * TRANSACTION SAFETY: Each action follows this order:
 *   1. Complete the jBPM task FIRST (if it fails, DB is untouched)
 *   2. Update the JPA entity AFTER task completion succeeds
 *   3. Log audit LAST
 *
 * This REDUCES (but does not eliminate) DB/BPM inconsistency risk:
 * - If step 1 fails → DB untouched. No inconsistency. ✓
 * - If step 2 fails after step 1 → BPM advanced but DB stale. ✗
 *   In this case, the jBPM process variables are AUTHORITATIVE.
 *   The ReconciliationService (run periodically or on-demand) detects
 *   and repairs such inconsistencies by comparing process state with DB.
 * - True atomicity would require JTA with XA transactions spanning both
 *   the BPM engine and the application DB, which adds complexity.
 *   For this MVP, the reconciliation approach is a pragmatic choice.
 */
public class ProcessBridgeService {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessBridgeService.class);
    private static final String PROCESS_ID = "com.gh.awb.lri.LiquidityRiskIndicators";

    private final RuntimeManager runtimeManager;
    private final LiquidityRiskReportRepository reportRepo;
    private final AuditService auditService;

    public ProcessBridgeService(RuntimeManager runtimeManager,
                                LiquidityRiskReportRepository reportRepo,
                                AuditService auditService) {
        this.runtimeManager = runtimeManager;
        this.reportRepo = reportRepo;
        this.auditService = auditService;
    }

    /**
     * Start a new process instance.
     * Order: create entity → start BPMN → link IDs → audit
     */
    public LiquidityRiskReport startProcess(Integer year, String quarterRange, String initiator) {
        LOG.info("Starting LRI process: year={}, range={}, initiator={}", year, quarterRange, initiator);

        int maxVersion = reportRepo.findMaxVersionByPeriod(year, quarterRange);
        LiquidityRiskReport report = new LiquidityRiskReport(year, quarterRange, initiator);
        report.setVersionNumber(maxVersion + 1);
        report = reportRepo.save(report);

        Map<String, Object> params = new HashMap<>();
        params.put("selectedYear", year);
        params.put("selectedQuarterRange", quarterRange);
        params.put("reportStatus", "DRAFT");
        params.put("isReturned", false);
        params.put("reportEntityId", report.getId());
        params.put("initiator", initiator);

        RuntimeEngine engine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
        try {
            KieSession kieSession = engine.getKieSession();
            ProcessInstance pi = kieSession.startProcess(PROCESS_ID, params);
            report.setProcessInstanceId(pi.getId());
            report = reportRepo.save(report);
            auditService.log(report, "CREATED", initiator, "Process instance #" + pi.getId());
            LOG.info("Process started: reportId={}, pid={}", report.getId(), pi.getId());
            return report;
        } catch (Exception e) {
            // Process start failed — delete the orphan report entity
            reportRepo.delete(report);
            throw new RuntimeException("Failed to start process: " + e.getMessage(), e);
        } finally {
            runtimeManager.disposeRuntimeEngine(engine);
        }
    }

    /**
     * Complete a human task. Called by all action methods.
     * This is step 1 — happens BEFORE any DB entity update.
     */
    private void driveProcess(Long processInstanceId, String userId, Map<String, Object> taskData) {
        RuntimeEngine engine = runtimeManager.getRuntimeEngine(
            ProcessInstanceIdContext.get(processInstanceId));
        try {
            TaskService taskService = engine.getTaskService();
            List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner(userId, "en-UK");
            TaskSummary activeTask = tasks.stream()
                .filter(t -> t.getProcessInstanceId().equals(processInstanceId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                    "No active task for process " + processInstanceId + " / user " + userId));

            long taskId = activeTask.getId();
            taskService.claim(taskId, userId);
            taskService.start(taskId, userId);
            taskService.complete(taskId, userId, taskData);
            LOG.info("Task {} completed by {} for process {}", taskId, userId, processInstanceId);
        } finally {
            runtimeManager.disposeRuntimeEngine(engine);
        }
    }

    public LiquidityRiskReport saveDraft(Long reportId, String comment, String userId) {
        LiquidityRiskReport report = findReport(reportId);

        // Step 1: Drive BPMN (loops HT-02 back to itself)
        Map<String, Object> td = Map.of("employeeComment", nvl(comment), "makerAction", "save");
        driveProcess(report.getProcessInstanceId(), userId, td);

        // Step 2: Update DB (only after task succeeded)
        report.setEmployeeComment(comment);
        report = reportRepo.save(report);
        auditService.log(report, "SAVED", userId, null);
        return report;
    }

    public LiquidityRiskReport submitToManager(Long reportId, String comment, String userId) {
        LiquidityRiskReport report = findReport(reportId);

        Map<String, Object> td = Map.of("employeeComment", nvl(comment), "makerAction", "submit");
        driveProcess(report.getProcessInstanceId(), userId, td);

        report.setEmployeeComment(comment);
        report.setStatus(ReportStatus.SUBMITTED);
        report.setSubmittedAt(LocalDateTime.now());
        report = reportRepo.save(report);
        auditService.log(report, "SUBMITTED", userId, comment);
        return report;
    }

    public LiquidityRiskReport managerApprove(Long reportId, String comment, String userId) {
        LiquidityRiskReport report = findReport(reportId);

        Map<String, Object> td = Map.of("managerComment", nvl(comment), "managerAction", "submit");
        driveProcess(report.getProcessInstanceId(), userId, td);

        report.setManagerComment(comment);
        report.setStatus(ReportStatus.UNDER_REVIEW);
        report = reportRepo.save(report);
        auditService.log(report, "REVIEWED", userId, comment);
        return report;
    }

    public LiquidityRiskReport returnToMaker(Long reportId, String returnComment, String userId) {
        LiquidityRiskReport report = findReport(reportId);

        Map<String, Object> td = Map.of("returnComment", returnComment, "managerAction", "return");
        driveProcess(report.getProcessInstanceId(), userId, td);

        report.setReturnComment(returnComment);
        report.setStatus(ReportStatus.DRAFT);
        report = reportRepo.save(report);
        auditService.log(report, "RETURNED", userId, returnComment);
        return report;
    }

    public LiquidityRiskReport directorApprove(Long reportId, String userId) {
        LiquidityRiskReport report = findReport(reportId);

        Map<String, Object> td = Map.of("approvedBy", userId);
        driveProcess(report.getProcessInstanceId(), userId, td);

        report.setStatus(ReportStatus.APPROVED);
        report.setApprovedAt(LocalDateTime.now());
        report.setApprovedBy(userId);
        report = reportRepo.save(report);
        auditService.log(report, "APPROVED", userId, "Final approval. Archive triggered by BPMN ST-04.");
        return report;
    }

    private LiquidityRiskReport findReport(Long id) {
        return reportRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Report not found: " + id));
    }

    private String nvl(String s) { return s != null ? s : ""; }
}
