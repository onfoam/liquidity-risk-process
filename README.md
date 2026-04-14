# Liquidity Risk Indicators (LRI) — Al-Wahda Bank

## Overview
jBPM-based workflow for monitoring three CBL regulatory liquidity ratios: LCR, NSFR, Leverage Ratio.
Three-level approval: Maker → Checker → Approver.

## Architecture
```
Angular Frontend → REST Controller → ProcessBridgeService → jBPM Runtime
                                          ↓                      ↓
                                    JPA (PostgreSQL)      BPMN2 Process Engine
                                                               ↓
                                                    Work Item Handlers
                                                    (Validate, Calculate,
                                                     Archive, Report, Notify)
```
**Key design:** All state changes go through ProcessBridgeService which drives the jBPM engine.
The REST layer NEVER modifies report status directly.

## Prerequisites
- JDK 11+, Maven 3.8+, PostgreSQL 14+
- KIE Server / Business Central 7.74.x
- Keycloak 21.x

## Setup

### 1. Database
```bash
createdb lri_db
mvn liquibase:update -Dliquibase.url=jdbc:postgresql://localhost:5432/lri_db -Dliquibase.username=lri_user -Dliquibase.password=<pwd>
```
Also create the SRS source table (see section below).

### 2. Application Server Datasource
In WildFly/JBoss standalone.xml:
```xml
<datasource jndi-name="java:jboss/datasources/LriDS" pool-name="LriDS">
    <connection-url>jdbc:postgresql://localhost:5432/lri_db</connection-url>
    <driver>postgresql</driver>
    <security><user-name>lri_user</user-name><password>***</password></security>
</datasource>
```

### 3. Keycloak
```bash
kcadm.sh create groups -r your-realm -f config/lri-keycloak-config.json
```

### 4. SMTP
```
-Dlri.smtp.host=mail.alwahdabank.ly -Dlri.smtp.port=587
-Dlri.smtp.auth=true -Dlri.smtp.starttls=true
-Dlri.smtp.user=lri-system -Dlri.smtp.password=***
-Dlri.smtp.from=lri-noreply@alwahdabank.ly
```

### 5. Build & Deploy
```bash
mvn clean install
curl -X PUT http://localhost:8080/kie-server/services/rest/server/containers/lri_1.0.0 \
    -H "Content-Type: application/json" \
    -d '{"release-id":{"group-id":"com.gh.awb","artifact-id":"liquidity-risk-indicators","version":"1.0.0"}}'
```

## API Endpoints
| Method | Endpoint | Role | Action |
|--------|----------|------|--------|
| POST | /api/lri/reports | maker | Create report + start jBPM process |
| POST | /api/lri/reports/{id}/save | maker | Save draft (loops HT-02) |
| POST | /api/lri/reports/{id}/submit | maker | Submit to manager (completes HT-02) |
| POST | /api/lri/reports/{id}/approve-manager | checker | Forward to director (completes HT-03) |
| POST | /api/lri/reports/{id}/return | checker | Return to maker (completes HT-03, loops) |
| POST | /api/lri/reports/{id}/approve-director | approver | Final approval (completes HT-04 → ST-04) |
| GET | /api/lri/reports/{id} | all | Get report |
| GET | /api/lri/reports/by-process/{pid} | all | Get by process instance |
| GET | /api/lri/reports/archive | all | List archived (paged) |
| GET | /api/lri/reports/check-duplicate | maker | Check period (active + archived) |
| GET | /api/lri/reports/{id}/audit | checker, approver | Full audit trail |
| GET | /api/lri/reports/{id}/download | checker, approver | Download Excel |

## Implementation Status
| Component | Status | Notes |
|-----------|--------|-------|
| BPMN2 process | ✅ Complete | 15 nodes, 17 flows, 3 timers, diagram interchange |
| ProcessBridgeService | ✅ Complete | REST↔jBPM bridge, all 6 actions |
| DataValidationHandler | ✅ Complete | Reads SRS Phase 1 DB via EntityManager |
| CalculationHandler | ✅ Complete | Uses LiquidityCalculationService |
| ArchiveReportHandler | ✅ Complete | JPA via EntityManagerFactory |
| ReportGenerationHandler | ✅ Complete | Apache POI Excel generation |
| NotificationHandler | ✅ Complete | Thymeleaf + JavaMail, TLS, auth, retry |
| REST Controller | ✅ Complete | 12 endpoints, @RolesAllowed on all |
| JPA Entities | ✅ Complete | @ManyToOne audit relationship |
| Liquibase | ✅ Complete | 2 changesets with rollback |
| Keycloak config | ✅ Complete | 3 groups, 3 roles |
| Email templates | ✅ Complete | 5 bilingual templates |
| Unit tests | ✅ Complete | 9 tests (CalculationService) |
| Integration tests | ✅ Complete | 3 tests (ProcessBridgeService) |
| OpenKM integration | ⏳ Post-MVP | JPA archive only for now |
| Angular frontend | ⏳ Separate | Specs in Phase 3 document |

## Calculation Formula (CBL Methodology)
```
Delta1 = (Month2 - Month1) / Month2    ← denominator is CURRENT month
Delta2 = (Month3 - Month2) / Month3    ← intentional per CBL methodology
Quarter_Value = Average(Delta1, Delta2)
Coverage = Quarter_B - Quarter_A
```
**This is NOT standard growth rate.** The CBL uses current-month denominator.
See SRS V2.3, Section 9, Page 24.

## SRS Phase 1 Database
```sql
CREATE TABLE srs_monthly_indicators (
    indicator_name VARCHAR(20),  -- LCR, NSFR, LEVERAGE
    report_year    INT,
    report_month   INT,
    value          DOUBLE PRECISION,
    status         VARCHAR(20)   -- must be APPROVED to be consumed
);
```
