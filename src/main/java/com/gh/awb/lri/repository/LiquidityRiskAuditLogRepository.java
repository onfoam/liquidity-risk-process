package com.gh.awb.lri.repository;

import com.gh.awb.lri.entity.LiquidityRiskAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LiquidityRiskAuditLogRepository extends JpaRepository<LiquidityRiskAuditLog, Long> {
    List<LiquidityRiskAuditLog> findByReportIdOrderByPerformedAtAsc(Long reportId);
}
