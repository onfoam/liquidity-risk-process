package com.gh.awb.lri.rest;

import com.gh.awb.lri.security.LriRoles;
import com.gh.awb.lri.service.ReconciliationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Map;

/**
 * Admin-only endpoints for system maintenance.
 */
@RestController
@RequestMapping("/api/lri/admin")
public class AdminController {

    private final ReconciliationService reconciliationService;

    public AdminController(ReconciliationService reconciliationService) {
        this.reconciliationService = reconciliationService;
    }

    /**
     * Trigger manual reconciliation between DB and BPM states.
     * Should also be scheduled as a daily cron job in production.
     */
    @PostMapping("/reconcile")
    @RolesAllowed(LriRoles.APPROVER)
    public ResponseEntity<Map<String, Object>> reconcile() {
        ReconciliationService.ReconciliationResult result = reconciliationService.reconcile();
        return ResponseEntity.ok(Map.of(
            "checked", result.checked,
            "repaired", result.repaired,
            "errors", result.errors));
    }
}
