package com.gh.awb.lri;

import com.gh.awb.lri.entity.LiquidityRiskReport;
import com.gh.awb.lri.entity.ReportStatus;
import com.gh.awb.lri.repository.LiquidityRiskReportRepository;
import com.gh.awb.lri.service.AuditService;
import com.gh.awb.lri.service.ProcessBridgeService;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for ProcessBridgeService — verifies that REST actions correctly
 * drive the jBPM workflow engine.
 */
public class ProcessBridgeServiceTest {

    private ProcessBridgeService service;
    private RuntimeManager runtimeManager;
    private LiquidityRiskReportRepository reportRepo;
    private AuditService auditService;

    @Before
    public void setUp() {
        runtimeManager = mock(RuntimeManager.class);
        reportRepo = mock(LiquidityRiskReportRepository.class);
        auditService = mock(AuditService.class);
        service = new ProcessBridgeService(runtimeManager, reportRepo, auditService);
    }

    @Test
    public void startProcess_createsReportAndStartsBpmn() {
        // Mock RuntimeEngine and KieSession
        RuntimeEngine engine = mock(RuntimeEngine.class);
        KieSession kieSession = mock(KieSession.class);
        ProcessInstance processInstance = mock(ProcessInstance.class);

        when(runtimeManager.getRuntimeEngine(any())).thenReturn(engine);
        when(engine.getKieSession()).thenReturn(kieSession);
        when(kieSession.startProcess(anyString(), anyMap())).thenReturn(processInstance);
        when(processInstance.getId()).thenReturn(1001L);
        when(reportRepo.findMaxVersionByPeriod(2025, "Q1-Q2")).thenReturn(0);
        when(reportRepo.save(any(LiquidityRiskReport.class))).thenAnswer(inv -> {
            LiquidityRiskReport r = inv.getArgument(0);
            r.setId(42L);
            return r;
        });

        LiquidityRiskReport result = service.startProcess(2025, "Q1-Q2", "maker1");

        assertThat(result.getReportYear()).isEqualTo(2025);
        assertThat(result.getQuarterRange()).isEqualTo("Q1-Q2");
        assertThat(result.getVersionNumber()).isEqualTo(1);
        assertThat(result.getProcessInstanceId()).isEqualTo(1001L);
        assertThat(result.getStatus()).isEqualTo(ReportStatus.DRAFT);

        verify(kieSession).startProcess(eq("com.gh.awb.lri.LiquidityRiskIndicators"), anyMap());
        verify(auditService).log(any(), eq("CREATED"), eq("maker1"), contains("1001"));
        verify(runtimeManager).disposeRuntimeEngine(engine);
    }

    @Test
    public void submitToManager_updatesStatusAndDrivesProcess() {
        LiquidityRiskReport report = new LiquidityRiskReport(2025, "Q1-Q2", "maker1");
        report.setId(42L);
        report.setProcessInstanceId(1001L);

        when(reportRepo.findById(42L)).thenReturn(Optional.of(report));
        when(reportRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // Mock task completion (simplified — full test needs TaskService mock)
        RuntimeEngine engine = mock(RuntimeEngine.class);
        when(runtimeManager.getRuntimeEngine(any())).thenReturn(engine);
        org.kie.api.task.TaskService taskService = mock(org.kie.api.task.TaskService.class);
        when(engine.getTaskService()).thenReturn(taskService);
        org.kie.api.task.model.TaskSummary task = mock(org.kie.api.task.model.TaskSummary.class);
        when(task.getProcessInstanceId()).thenReturn(1001L);
        when(task.getId()).thenReturn(501L);
        when(taskService.getTasksAssignedAsPotentialOwner(anyString(), anyString()))
            .thenReturn(java.util.List.of(task));

        LiquidityRiskReport result = service.submitToManager(42L, "LCR declined due to seasonal factors", "maker1");

        assertThat(result.getStatus()).isEqualTo(ReportStatus.SUBMITTED);
        assertThat(result.getEmployeeComment()).isEqualTo("LCR declined due to seasonal factors");
        assertThat(result.getSubmittedAt()).isNotNull();

        verify(taskService).claim(501L, "maker1");
        verify(taskService).start(501L, "maker1");
        verify(taskService).complete(eq(501L), eq("maker1"), anyMap());
    }

    @Test
    public void returnToMaker_setsReturnCommentAndDrivesProcess() {
        LiquidityRiskReport report = new LiquidityRiskReport(2025, "Q1-Q2", "maker1");
        report.setId(42L);
        report.setProcessInstanceId(1001L);
        report.setStatus(ReportStatus.SUBMITTED);

        when(reportRepo.findById(42L)).thenReturn(Optional.of(report));
        when(reportRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        RuntimeEngine engine = mock(RuntimeEngine.class);
        when(runtimeManager.getRuntimeEngine(any())).thenReturn(engine);
        org.kie.api.task.TaskService taskService = mock(org.kie.api.task.TaskService.class);
        when(engine.getTaskService()).thenReturn(taskService);
        org.kie.api.task.model.TaskSummary task = mock(org.kie.api.task.model.TaskSummary.class);
        when(task.getProcessInstanceId()).thenReturn(1001L);
        when(task.getId()).thenReturn(502L);
        when(taskService.getTasksAssignedAsPotentialOwner(anyString(), anyString()))
            .thenReturn(java.util.List.of(task));

        LiquidityRiskReport result = service.returnToMaker(42L, "Need more detail on LCR", "checker1");

        assertThat(result.getStatus()).isEqualTo(ReportStatus.DRAFT);
        assertThat(result.getReturnComment()).isEqualTo("Need more detail on LCR");
        verify(auditService).log(any(), eq("RETURNED"), eq("checker1"), eq("Need more detail on LCR"));
    }
}
