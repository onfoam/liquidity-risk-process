package com.gh.awb.lri.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "lri_audit_log")
public class LiquidityRiskAuditLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false) private LiquidityRiskReport report;
    @Column(name = "action", length = 30, nullable = false) private String action;
    @Column(name = "performed_by", length = 100, nullable = false) private String performedBy;
    @Column(name = "performed_at", nullable = false) private LocalDateTime performedAt;
    @Column(name = "comment", length = 500) private String comment;

    public LiquidityRiskAuditLog() {}
    public Long getId() { return id; }
    public LiquidityRiskReport getReport() { return report; }
    public String getAction() { return action; }
    public String getPerformedBy() { return performedBy; }
    public LocalDateTime getPerformedAt() { return performedAt; }
    public String getComment() { return comment; }
}
