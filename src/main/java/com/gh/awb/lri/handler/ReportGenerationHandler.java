package com.gh.awb.lri.handler;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.manager.RuntimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/** Generates report (placeholder for server extension). Work Item: GenerateReport */
public class ReportGenerationHandler implements WorkItemHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ReportGenerationHandler.class);
    private final RuntimeManager runtimeManager;
    public ReportGenerationHandler(RuntimeManager rm) { this.runtimeManager = rm; }

    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.info("GenerateReport: Executing {}", workItem.getId());
        Map<String, Object> results = new HashMap<>();
        try {
            // Business logic implemented in server extension JAR
            // This handler delegates to the extension at runtime
            manager.completeWorkItem(workItem.getId(), results);
        } catch (Exception e) {
            LOG.error("GenerateReport: Failed - {}", e.getMessage(), e);
            manager.abortWorkItem(workItem.getId());
        }
    }

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.warn("GenerateReport: Aborted {}", workItem.getId());
    }
}
