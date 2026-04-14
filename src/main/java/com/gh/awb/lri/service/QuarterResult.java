package com.gh.awb.lri.service;
import java.util.ArrayList; import java.util.List;
public class QuarterResult {
    private final Double value; private final List<String> warnings;
    public QuarterResult(Double value, List<String> warnings) { this.value = value; this.warnings = warnings != null ? warnings : new ArrayList<>(); }
    public Double getValue() { return value; }
    public List<String> getWarnings() { return warnings; }
    public boolean hasWarnings() { return !warnings.isEmpty(); }
}
