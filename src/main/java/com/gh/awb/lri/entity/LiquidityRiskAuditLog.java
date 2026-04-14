package com.gh.awb.lri.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lri_audit_log")
public class LiquidityRiskAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private LiquidityRiskReport report;

    @Column(name = "action", length = 30, nullable = false)
    private String action;

    @Column(name = "performed_by", length = 100, nullable = false)
    private String performedBy;

    @Column(name = "performed_at", nullable = false)
    private LocalDateTime performedAt;

    @Column(name = "comment", length = 500)
    private String comment;

    public LiquidityRiskAuditLog() {}

    public LiquidityRiskAuditLog(LiquidityRiskReport report, String action, String performedBy, String comment) {
        this.report = report;
        this.action = action;
        this.performedBy = performedBy;
        this.performedAt = LocalDateTime.now();
        this.comment = comment;
    }

    public Long getId() { return id; }
    public LiquidityRiskReport getReport() { return report; }
    public String getAction() { return action; }
    public String getPerformedBy() { return performedBy; }
    public LocalDateTime getPerformedAt() { return performedAt; }
    public String getComment() { return comment; }
}
