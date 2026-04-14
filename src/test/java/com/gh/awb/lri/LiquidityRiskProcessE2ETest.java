package com.gh.awb.lri;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * End-to-end BPMN process test using jBPM test framework.
 * Runs the ACTUAL .bpmn2 file on an in-memory KIE runtime with mock handlers.
 */
public class LiquidityRiskProcessE2ETest extends JbpmJUnitBaseTestCase {

    private static final String PROCESS_ID = "com.gh.awb.lri.LiquidityRiskIndicators";
    private static final String BPMN_PATH = "com/gh/awb/lri/liquidity-risk-indicators.bpmn2";

    public LiquidityRiskProcessE2ETest() {
        super(true, true); // persistence=true, humanTasks=true
    }

    @Before
    public void setup() { createRuntimeManager(BPMN_PATH); }

    @After
    public void cleanup() { disposeRuntimeManager(); }

    // ═══════════════════════════════════════
    // FULL HAPPY PATH: Start → HT-01 → ST-01 → ST-02 → HT-02(submit) → HT-03(submit) → HT-04 → ST-04 → END
    // ═══════════════════════════════════════
    @Test
    public void testFullHappyPath_StartToArchive() {
        RuntimeEngine engine = getRuntimeEngine();
        KieSession ks = engine.getKieSession();
        TaskService ts = engine.getTaskService();

        // Register ALL work item handlers
        registerAllHandlers(ks);

        // START process
        ProcessInstance pi = ks.startProcess(PROCESS_ID, processParams());
        long pid = pi.getId();
        assertProcessInstanceActive(pid, ks);

        // HT-01: Maker selects period
        completeTask(ts, "maker1", Map.of(
            "selectedYear", 2025, "selectedQuarterRange", "Q1-Q2"));

        // ST-01 (DataValidation) + GW-01 (yes) + ST-02 (Calculate) execute automatically
        // HT-02 should now be active for maker

        // HT-02: Maker reviews indicators and SUBMITS
        completeTask(ts, "maker1", Map.of(
            "employeeComment", "LCR decline due to seasonal factors",
            "makerAction", "submit"));

        // HT-03 should now be active for checker

        // HT-03: Manager reviews and APPROVES (submits to director)
        completeTask(ts, "checker1", Map.of(
            "managerComment", "Reviewed and concur with analysis",
            "managerAction", "submit"));

        // HT-04 should now be active for approver

        // HT-04: Director gives FINAL APPROVAL
        completeTask(ts, "approver1", Map.of(
            "approvedBy", "approver1"));

        // ST-04 (Archive) executes automatically → process should END
        assertProcessInstanceCompleted(pid, ks);
    }

    // ═══════════════════════════════════════
    // MANAGER RETURN LOOP: HT-03(return) → HT-02(resubmit) → HT-03(submit)
    // ═══════════════════════════════════════
    @Test
    public void testManagerReturn_ThenResubmit_ThenApprove() {
        RuntimeEngine engine = getRuntimeEngine();
        KieSession ks = engine.getKieSession();
        TaskService ts = engine.getTaskService();
        registerAllHandlers(ks);

        ProcessInstance pi = ks.startProcess(PROCESS_ID, processParams());
        long pid = pi.getId();

        // HT-01: Select period
        completeTask(ts, "maker1", Map.of(
            "selectedYear", 2025, "selectedQuarterRange", "Q1-Q2"));

        // HT-02: Maker submits
        completeTask(ts, "maker1", Map.of(
            "employeeComment", "Initial comment", "makerAction", "submit"));

        // HT-03: Manager RETURNS to maker
        completeTask(ts, "checker1", Map.of(
            "returnComment", "Please elaborate on LCR decline",
            "managerAction", "return"));

        // HT-02 should reappear for maker (return loop)
        List<TaskSummary> makerTasks = ts.getTasksAssignedAsPotentialOwner("maker1", "en-UK");
        assertFalse("After return, maker should have HT-02 again", makerTasks.isEmpty());
        assertProcessInstanceActive(pid, ks);

        // HT-02: Maker re-submits with updated comment
        completeTask(ts, "maker1", Map.of(
            "employeeComment", "LCR decline is due to Q2 seasonal liability increase of 15%",
            "makerAction", "submit"));

        // HT-03: Manager now approves
        completeTask(ts, "checker1", Map.of(
            "managerComment", "Satisfactory explanation", "managerAction", "submit"));

        // HT-04: Director approves
        completeTask(ts, "approver1", Map.of("approvedBy", "approver1"));

        // Process should complete
        assertProcessInstanceCompleted(pid, ks);
    }

    // ═══════════════════════════════════════
    // SAVE LOOP: HT-02(save) → HT-02(save) → HT-02(submit)
    // ═══════════════════════════════════════
    @Test
    public void testSaveDraft_MultipleTimes_ThenSubmit() {
        RuntimeEngine engine = getRuntimeEngine();
        KieSession ks = engine.getKieSession();
        TaskService ts = engine.getTaskService();
        registerAllHandlers(ks);

        ProcessInstance pi = ks.startProcess(PROCESS_ID, processParams());
        long pid = pi.getId();

        // HT-01
        completeTask(ts, "maker1", Map.of(
            "selectedYear", 2025, "selectedQuarterRange", "Q2-Q3"));

        // HT-02: Save draft 1
        completeTask(ts, "maker1", Map.of(
            "employeeComment", "Draft v1", "makerAction", "save"));
        assertProcessInstanceActive(pid, ks);

        // HT-02: Save draft 2 (task loops back)
        completeTask(ts, "maker1", Map.of(
            "employeeComment", "Draft v2 - more detail", "makerAction", "save"));
        assertProcessInstanceActive(pid, ks);

        // HT-02: Now submit
        completeTask(ts, "maker1", Map.of(
            "employeeComment", "Final version", "makerAction", "submit"));

        // HT-03 should be active for checker
        List<TaskSummary> checkerTasks = ts.getTasksAssignedAsPotentialOwner("checker1", "en-UK");
        assertFalse("After submit, checker should have HT-03", checkerTasks.isEmpty());
    }

    // ═══════════════════════════════════════
    // ERROR PATH: Missing data → Error End
    // ═══════════════════════════════════════
    @Test
    public void testMissingData_ProcessEndsWithError() {
        RuntimeEngine engine = getRuntimeEngine();
        KieSession ks = engine.getKieSession();
        TaskService ts = engine.getTaskService();

        // Register DataValidation that returns false
        ks.getWorkItemManager().registerWorkItemHandler("DataValidation", (wi, mgr) -> {
            mgr.completeWorkItem(wi.getId(), Map.of("dataAvailable", false));
        });
        registerAutoCompleteHandlers(ks, "CalculateIndicators", "ArchiveReport", "GenerateReport", "SendNotification");

        ProcessInstance pi = ks.startProcess(PROCESS_ID, processParams());
        long pid = pi.getId();

        // HT-01
        completeTask(ts, "maker1", Map.of(
            "selectedYear", 2025, "selectedQuarterRange", "Q3-Q4"));

        // ST-01 sets dataAvailable=false → GW-01 → Error End
        assertProcessInstanceCompleted(pid, ks);
    }

    // ═══ HELPERS ═══

    private Map<String, Object> processParams() {
        Map<String, Object> p = new HashMap<>();
        p.put("selectedYear", 2025);
        p.put("selectedQuarterRange", "Q1-Q2");
        p.put("reportStatus", "DRAFT");
        p.put("isReturned", false);
        p.put("reportEntityId", 1L);
        p.put("initiator", "maker1");
        return p;
    }

    private void completeTask(TaskService ts, String userId, Map<String, Object> data) {
        List<TaskSummary> tasks = ts.getTasksAssignedAsPotentialOwner(userId, "en-UK");
        assertFalse("User " + userId + " should have tasks", tasks.isEmpty());
        long tid = tasks.get(0).getId();
        ts.claim(tid, userId);
        ts.start(tid, userId);
        ts.complete(tid, userId, data);
    }

    private void registerAllHandlers(KieSession ks) {
        // DataValidation: returns valid data
        ks.getWorkItemManager().registerWorkItemHandler("DataValidation", (wi, mgr) -> {
            Map<String, Object> r = new HashMap<>();
            r.put("dataAvailable", true);
            r.put("monthlyData", Map.of(
                "LCR", new double[]{100,110,105,108,112,106},
                "NSFR", new double[]{200,210,205,208,215,210},
                "LEVERAGE", new double[]{50,52,51,53,55,54}));
            mgr.completeWorkItem(wi.getId(), r);
        });

        // CalculateIndicators: returns computed values
        ks.getWorkItemManager().registerWorkItemHandler("CalculateIndicators", (wi, mgr) -> {
            Map<String, Object> r = new HashMap<>();
            r.put("lcrQA", 0.045); r.put("lcrQB", 0.032); r.put("lcrCoverage", -0.013);
            r.put("nsfrQA", 0.025); r.put("nsfrQB", 0.038); r.put("nsfrCoverage", 0.013);
            r.put("leverageQA", 0.020); r.put("leverageQB", 0.019); r.put("leverageCoverage", -0.001);
            r.put("calcWarnings", List.of());
            mgr.completeWorkItem(wi.getId(), r);
        });

        // Archive, Report, Notification: auto-complete
        registerAutoCompleteHandlers(ks, "ArchiveReport", "GenerateReport", "SendNotification");
    }

    private void registerAutoCompleteHandlers(KieSession ks, String... names) {
        for (String name : names) {
            ks.getWorkItemManager().registerWorkItemHandler(name, (wi, mgr) ->
                mgr.completeWorkItem(wi.getId(), new HashMap<>()));
        }
    }
}
