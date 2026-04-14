package com.gh.awb.lri;

import com.gh.awb.lri.service.LiquidityCalculationService;
import com.gh.awb.lri.service.QuarterResult;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class LiquidityCalculationServiceTest {
    private LiquidityCalculationService svc;

    @Before public void setUp() { svc = new LiquidityCalculationService(); }

    @Test public void normalCalc() {
        QuarterResult r = svc.calculateQuarter(100, 110, 105);
        assertThat(r.getValue()).isCloseTo(0.02165, within(0.001));
        assertThat(r.hasWarnings()).isFalse();
    }

    @Test public void equalMonths() {
        QuarterResult r = svc.calculateQuarter(100, 100, 100);
        assertThat(r.getValue()).isCloseTo(0.0, within(0.0001));
    }

    @Test public void decreasingTrend() {
        QuarterResult r = svc.calculateQuarter(200, 150, 100);
        assertThat(r.getValue()).isCloseTo(-0.4167, within(0.001));
    }

    @Test public void month2Zero() {
        QuarterResult r = svc.calculateQuarter(100, 0, 105);
        assertThat(r.getValue()).isCloseTo(1.0, within(0.001));
        assertThat(r.getWarnings()).anyMatch(w -> w.contains("Month 2"));
    }

    @Test public void month3Zero() {
        QuarterResult r = svc.calculateQuarter(100, 110, 0);
        assertThat(r.getValue()).isCloseTo(0.0909, within(0.001));
    }

    @Test public void bothZero() {
        QuarterResult r = svc.calculateQuarter(100, 0, 0);
        assertThat(r.getValue()).isNull();
        assertThat(r.getWarnings()).hasSize(2);
    }

    @Test public void coverage() { assertThat(svc.calculateCoverage(0.05, 0.08)).isCloseTo(0.03, within(0.0001)); }
    @Test public void negativeCoverage() { assertThat(svc.calculateCoverage(0.10, 0.05)).isCloseTo(-0.05, within(0.0001)); }
    @Test public void nullCoverage() { assertThat(svc.calculateCoverage(null, 0.08)).isNull(); }
}
