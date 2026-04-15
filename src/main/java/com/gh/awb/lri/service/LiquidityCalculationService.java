package com.gh.awb.lri.service;

import java.util.ArrayList;
import java.util.List;

public class LiquidityCalculationService {
    public QuarterResult calculateQuarter(double m1, double m2, double m3) {
        List<String> warnings = new ArrayList<>();
        Double delta1 = null, delta2 = null;
        if (m2 == 0.0) { warnings.add("Month 2 value is zero; delta1 cannot be computed."); }
        else { delta1 = (m2 - m1) / m2; }
        if (m3 == 0.0) { warnings.add("Month 3 value is zero; delta2 cannot be computed."); }
        else { delta2 = (m3 - m2) / m3; }
        Double qv = null;
        if (delta1 != null && delta2 != null) { qv = (delta1 + delta2) / 2.0; }
        else if (delta1 != null) { qv = delta1; warnings.add("Quarter value based on single delta only."); }
        else if (delta2 != null) { qv = delta2; warnings.add("Quarter value based on single delta only."); }
        return new QuarterResult(qv, warnings);
    }
    public Double calculateCoverage(Double qa, Double qb) {
        if (qa == null || qb == null) return null;
        return qb - qa;
    }
}
