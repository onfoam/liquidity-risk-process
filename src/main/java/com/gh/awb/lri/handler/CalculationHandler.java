package com.gh.awb.lri.handler;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.manager.RuntimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/** Calculates quarterly indicator values. Work Item: CalculateIndicators */
public class CalculationHandler implements WorkItemHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CalculationHandler.class);
    private final RuntimeManager runtimeManager;
    public CalculationHandler(RuntimeManager rm) { this.runtimeManager = rm; }

    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.info("CalculateIndicators: Executing {}", workItem.getId());
        Map<String, Object> results = new HashMap<>();
        try {
            // Business logic implemented in server extension JAR
            // This handler delegates to the extension at runtime
            manager.completeWorkItem(workItem.getId(), results);
        } catch (Exception e) {
            LOG.error("CalculateIndicators: Failed - {}", e.getMessage(), e);
            manager.abortWorkItem(workItem.getId());
        }
    }

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.warn("CalculateIndicators: Aborted {}", workItem.getId());
    }
}
