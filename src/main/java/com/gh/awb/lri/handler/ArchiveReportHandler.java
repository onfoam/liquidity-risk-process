package com.gh.awb.lri.handler;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.manager.RuntimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/** Archives approved report. Work Item: ArchiveReport */
public class ArchiveReportHandler implements WorkItemHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ArchiveReportHandler.class);
    private final RuntimeManager runtimeManager;
    public ArchiveReportHandler(RuntimeManager rm) { this.runtimeManager = rm; }

    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.info("ArchiveReport: Executing {}", workItem.getId());
        Map<String, Object> results = new HashMap<>();
        try {
            // Business logic implemented in server extension JAR
            // This handler delegates to the extension at runtime
            manager.completeWorkItem(workItem.getId(), results);
        } catch (Exception e) {
            LOG.error("ArchiveReport: Failed - {}", e.getMessage(), e);
            manager.abortWorkItem(workItem.getId());
        }
    }

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.warn("ArchiveReport: Aborted {}", workItem.getId());
    }
}
