package com.nswlrs.api.common.commonservice.repository.statement;

/**
 * The type User audit log statement.
 */
public class UserAuditLogStatement {

    /**
     * The constant INSERT_INTO_USER_AUDIT_LOG.
     */
    public static final String INSERT_INTO_USER_AUDIT_LOG = """                
                INSERT INTO USER_AUDIT_LOG (ACTION_DATE, ACTIONED_BY, USER_CODE, PROCESS_NAME, 
                            DB_TBLE, DB_ACTION_SIG, ACTIVITY_DETAILS) VALUES (TO_DATE(SUBSTR(?,1,20), 'DD-MON-RR 
                            HH24.MI.SS'), ?, ?, ?, ?, ?, ?)
                            """;

}
