package com.gh.awb.lri.repository;

import com.gh.awb.lri.entity.LiquidityRiskReport;
import com.gh.awb.lri.entity.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LiquidityRiskReportRepository extends JpaRepository<LiquidityRiskReport, Long> {

    Optional<LiquidityRiskReport> findByProcessInstanceId(Long pid);

    List<LiquidityRiskReport> findByReportYearAndQuarterRangeAndStatus(
        Integer year, String range, ReportStatus status);

    Page<LiquidityRiskReport> findByStatusOrderByApprovedAtDesc(
        ReportStatus status, Pageable pageable);

    /** Check for ANY active report (not just ARCHIVED) for the same period */
    @Query("SELECT COUNT(r) FROM LiquidityRiskReport r " +
           "WHERE r.reportYear = :year AND r.quarterRange = :range " +
           "AND r.status NOT IN (com.gh.awb.lri.entity.ReportStatus.ARCHIVED)")
    long countActiveByPeriod(@Param("year") Integer year, @Param("range") String range);

    /** Check archived reports for the same period */
    long countByReportYearAndQuarterRangeAndStatus(
        Integer year, String range, ReportStatus status);

    /** Get max version number for a period */
    @Query("SELECT COALESCE(MAX(r.versionNumber), 0) FROM LiquidityRiskReport r " +
           "WHERE r.reportYear = :year AND r.quarterRange = :range")
    int findMaxVersionByPeriod(@Param("year") Integer year, @Param("range") String range);
}
