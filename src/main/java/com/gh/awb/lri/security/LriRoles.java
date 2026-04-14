package com.gh.awb.lri.security;

/**
 * Centralized role constants for the LRI system.
 * Mapped to Keycloak realm roles defined in lri-keycloak-config.json.
 */
public final class LriRoles {
    private LriRoles() {}

    public static final String MAKER    = "liquidity-risk-maker";
    public static final String CHECKER  = "liquidity-risk-checker";
    public static final String APPROVER = "liquidity-risk-approver";

    /** All LRI roles — for endpoints accessible by any authenticated LRI user */
    public static final String[] ALL = { MAKER, CHECKER, APPROVER };
}
