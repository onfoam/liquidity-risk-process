package com.gh.awb.lri.handler;

import com.gh.awb.lri.service.LiquidityCalculationService;
import com.gh.awb.lri.service.QuarterResult;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.manager.RuntimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Calculates quarterly liquidity indicator values using LiquidityCalculationService.
 *
 * Input:  monthlyData (Map<String, double[]>), selectedQuarterRange (String)
 * Output: lcrQA, lcrQB, lcrCoverage, nsfrQA, nsfrQB, nsfrCoverage,
 *         leverageQA, leverageQB, leverageCoverage, calcWarnings (List<String>)
 *
 * Calculation formula (per SRS Section 9):
 *   Delta1 = (M2 - M1) / M2  -- change relative to CURRENT month, not previous
 *   Delta2 = (M3 - M2) / M3  -- this is intentional per CBL methodology
 *   Q_Value = Average(Delta1, Delta2)
 *   Coverage = Q(n+1) - Q(n)
 *
 * NOTE: The denominator is the CURRENT month (M2, M3), not the PREVIOUS month.
 * This differs from standard growth rate formulas. This is per Al-Wahda Bank / CBL
 * regulatory methodology as specified in SRS V2.3, Section 9, Page 24.
 */
public class CalculationHandler implements WorkItemHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CalculationHandler.class);
    private final RuntimeManager runtimeManager;
    private final LiquidityCalculationService calcService = new LiquidityCalculationService();

    public CalculationHandler(RuntimeManager runtimeManager) {
        this.runtimeManager = runtimeManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.info("CalculateIndicators: Starting calculation for work item {}", workItem.getId());

        Map<String, double[]> monthlyData = (Map<String, double[]>) workItem.getParameter("monthlyData");
        Map<String, Object> results = new HashMap<>();
        List<String> allWarnings = new ArrayList<>();

        try {
            // Calculate for each indicator
            for (String indicator : new String[]{"LCR", "NSFR", "LEVERAGE"}) {
                double[] values = monthlyData.get(indicator);
                if (values == null || values.length < 6) {
                    allWarnings.add(indicator + ": Insufficient monthly data.");
                    continue;
                }

                // Quarter A: months[0], months[1], months[2]
                QuarterResult qa = calcService.calculateQuarter(values[0], values[1], values[2]);
                // Quarter B: months[3], months[4], months[5]
                QuarterResult qb = calcService.calculateQuarter(values[3], values[4], values[5]);
                // Coverage
                Double coverage = calcService.calculateCoverage(qa.getValue(), qb.getValue());

                // Map to process variables
                String prefix = indicator.toLowerCase().replace("leverage", "leverage");
                if ("LCR".equals(indicator)) prefix = "lcr";
                else if ("NSFR".equals(indicator)) prefix = "nsfr";

                results.put(prefix + "QA", qa.getValue());
                results.put(prefix + "QB", qb.getValue());
                results.put(prefix + "Coverage", coverage);

                // Collect warnings
                qa.getWarnings().forEach(w -> allWarnings.add(indicator + " QA: " + w));
                qb.getWarnings().forEach(w -> allWarnings.add(indicator + " QB: " + w));

                LOG.info("CalculateIndicators: {} QA={}, QB={}, Coverage={}",
                    indicator, qa.getValue(), qb.getValue(), coverage);
            }

            results.put("calcWarnings", allWarnings);
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
