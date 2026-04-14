package com.gh.awb.lri.entity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lri_report", uniqueConstraints = @UniqueConstraint(name = "uq_lri_report_period_version", columnNames = {"report_year", "quarter_range", "version_number"}))
public class LiquidityRiskReport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "report_year", nullable = false) private Integer reportYear;
    @Column(name = "quarter_range", length = 10, nullable = false) private String quarterRange;
    @Column(name = "version_number", nullable = false) private Integer versionNumber = 1;
    @Column(name = "lcr_qa") private Double lcrQA;
    @Column(name = "lcr_qb") private Double lcrQB;
    @Column(name = "lcr_coverage") private Double lcrCoverage;
    @Column(name = "nsfr_qa") private Double nsfrQA;
    @Column(name = "nsfr_qb") private Double nsfrQB;
    @Column(name = "nsfr_coverage") private Double nsfrCoverage;
    @Column(name = "leverage_qa") private Double leverageQA;
    @Column(name = "leverage_qb") private Double leverageQB;
    @Column(name = "leverage_coverage") private Double leverageCoverage;
    @Column(name = "employee_comment", length = 255) private String employeeComment;
    @Column(name = "manager_comment", length = 255) private String managerComment;
    @Column(name = "return_comment", length = 255) private String returnComment;
    @Enumerated(EnumType.STRING) @Column(name = "status", length = 20, nullable = false) private ReportStatus status = ReportStatus.DRAFT;
    @Column(name = "process_instance_id", unique = true) private Long processInstanceId;
    @Column(name = "created_by", length = 100, nullable = false) private String createdBy;
    @Column(name = "created_at", nullable = false) private LocalDateTime createdAt;
    @Column(name = "submitted_at") private LocalDateTime submittedAt;
    @Column(name = "approved_at") private LocalDateTime approvedAt;
    @Column(name = "approved_by", length = 100) private String approvedBy;

    public LiquidityRiskReport() {}
    public LiquidityRiskReport(Integer reportYear, String quarterRange, String createdBy) {
        this.reportYear = reportYear; this.quarterRange = quarterRange; this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now(); this.status = ReportStatus.DRAFT;
    }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Integer getReportYear() { return reportYear; } public void setReportYear(Integer v) { this.reportYear = v; }
    public String getQuarterRange() { return quarterRange; } public void setQuarterRange(String v) { this.quarterRange = v; }
    public Integer getVersionNumber() { return versionNumber; } public void setVersionNumber(Integer v) { this.versionNumber = v; }
    public Double getLcrQA() { return lcrQA; } public void setLcrQA(Double v) { this.lcrQA = v; }
    public Double getLcrQB() { return lcrQB; } public void setLcrQB(Double v) { this.lcrQB = v; }
    public Double getLcrCoverage() { return lcrCoverage; } public void setLcrCoverage(Double v) { this.lcrCoverage = v; }
    public Double getNsfrQA() { return nsfrQA; } public void setNsfrQA(Double v) { this.nsfrQA = v; }
    public Double getNsfrQB() { return nsfrQB; } public void setNsfrQB(Double v) { this.nsfrQB = v; }
    public Double getNsfrCoverage() { return nsfrCoverage; } public void setNsfrCoverage(Double v) { this.nsfrCoverage = v; }
    public Double getLeverageQA() { return leverageQA; } public void setLeverageQA(Double v) { this.leverageQA = v; }
    public Double getLeverageQB() { return leverageQB; } public void setLeverageQB(Double v) { this.leverageQB = v; }
    public Double getLeverageCoverage() { return leverageCoverage; } public void setLeverageCoverage(Double v) { this.leverageCoverage = v; }
    public String getEmployeeComment() { return employeeComment; } public void setEmployeeComment(String v) { this.employeeComment = v; }
    public String getManagerComment() { return managerComment; } public void setManagerComment(String v) { this.managerComment = v; }
    public String getReturnComment() { return returnComment; } public void setReturnComment(String v) { this.returnComment = v; }
    public ReportStatus getStatus() { return status; } public void setStatus(ReportStatus v) { this.status = v; }
    public Long getProcessInstanceId() { return processInstanceId; } public void setProcessInstanceId(Long v) { this.processInstanceId = v; }
    public String getCreatedBy() { return createdBy; } public void setCreatedBy(String v) { this.createdBy = v; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
    public LocalDateTime getSubmittedAt() { return submittedAt; } public void setSubmittedAt(LocalDateTime v) { this.submittedAt = v; }
    public LocalDateTime getApprovedAt() { return approvedAt; } public void setApprovedAt(LocalDateTime v) { this.approvedAt = v; }
    public String getApprovedBy() { return approvedBy; } public void setApprovedBy(String v) { this.approvedBy = v; }
}
