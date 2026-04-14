package com.gh.awb.lri.handler;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.manager.RuntimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.*;

/**
 * Validates that monthly regulatory data exists in SRS Phase 1 DB
 * for all 6 months covered by the selected quarter pair.
 *
 * Input:  selectedYear (Integer), selectedQuarterRange (String)
 * Output: dataAvailable (Boolean), monthlyData (Map<String, double[]>)
 *
 * Queries the SRS Phase 1 database table: srs_monthly_indicators
 * Expected columns: indicator_name, report_year, report_month, value
 */
public class DataValidationHandler implements WorkItemHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DataValidationHandler.class);
    private static final String[] INDICATORS = {"LCR", "NSFR", "LEVERAGE"};
    private final RuntimeManager runtimeManager;

    public DataValidationHandler(RuntimeManager runtimeManager) {
        this.runtimeManager = runtimeManager;
    }

    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.info("DataValidation: Starting validation for work item {}", workItem.getId());

        Integer year = (Integer) workItem.getParameter("selectedYear");
        String quarterRange = (String) workItem.getParameter("selectedQuarterRange");

        Map<String, Object> results = new HashMap<>();

        try {
            // Resolve months from quarter range
            int[] months = resolveMonths(quarterRange);
            if (months == null) {
                LOG.error("DataValidation: Invalid quarter range: {}", quarterRange);
                results.put("dataAvailable", false);
                manager.completeWorkItem(workItem.getId(), results);
                return;
            }

            // Query SRS Phase 1 DB for monthly data
            EntityManagerFactory emf = (EntityManagerFactory) runtimeManager
                .getEnvironment().getEnvironment().get("org.kie.api.persistence.EntityManagerFactory");
            EntityManager em = emf.createEntityManager();

            Map<String, double[]> monthlyData = new HashMap<>();
            boolean allAvailable = true;

            try {
                for (String indicator : INDICATORS) {
                    double[] values = new double[6];
                    for (int i = 0; i < 6; i++) {
                        Query q = em.createNativeQuery(
                            "SELECT value FROM srs_monthly_indicators " +
                            "WHERE indicator_name = :ind AND report_year = :yr AND report_month = :mo " +
                            "AND status = 'APPROVED'");
                        q.setParameter("ind", indicator);
                        q.setParameter("yr", year);
                        q.setParameter("mo", months[i]);

                        List<?> resultList = q.getResultList();
                        if (resultList.isEmpty()) {
                            LOG.warn("DataValidation: Missing data for {} {}/{}", indicator, year, months[i]);
                            allAvailable = false;
                            break;
                        }
                        values[i] = ((Number) resultList.get(0)).doubleValue();
                    }
                    if (!allAvailable) break;
                    monthlyData.put(indicator, values);
                }
            } finally {
                em.close();
            }

            results.put("dataAvailable", allAvailable);
            if (allAvailable) {
                results.put("monthlyData", monthlyData);
                LOG.info("DataValidation: All data available for {} {}", year, quarterRange);
            } else {
                LOG.warn("DataValidation: Missing data for {} {}", year, quarterRange);
            }

            manager.completeWorkItem(workItem.getId(), results);

        } catch (Exception e) {
            LOG.error("DataValidation: Failed - {}", e.getMessage(), e);
            results.put("dataAvailable", false);
            manager.completeWorkItem(workItem.getId(), results);
        }
    }

    /**
     * Resolve quarter range to month numbers.
     * Q1-Q2 -> [1,2,3,4,5,6], Q2-Q3 -> [4,5,6,7,8,9], Q3-Q4 -> [7,8,9,10,11,12]
     */
    private int[] resolveMonths(String quarterRange) {
        switch (quarterRange) {
            case "Q1-Q2": return new int[]{1, 2, 3, 4, 5, 6};
            case "Q2-Q3": return new int[]{4, 5, 6, 7, 8, 9};
            case "Q3-Q4": return new int[]{7, 8, 9, 10, 11, 12};
            default: return null;
        }
    }

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.warn("DataValidation: Aborted {}", workItem.getId());
    }
}
