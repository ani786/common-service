package com.mycommonservref.api.common.commonservice.repository;

import com.mycommonservref.api.common.commonservice.model.UserAuditLog;
import com.mycommonservref.api.common.commonservice.repository.statement.UserAuditLogStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The type User audit log repository.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserAuditLogRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * This is the method to insert data to the UserAuditLog table.
     *
     * @param userAuditLog UserAuditLog cantaining the audit information
     * @return the number success of records
     */
    public Integer insert(UserAuditLog userAuditLog) {
        log.info("insert into the audit log with the user code: {} ", userAuditLog.getUserCode());
        return jdbcTemplate.update(UserAuditLogStatement.INSERT_INTO_USER_AUDIT_LOG, userAuditLog.getActionDate(),
                    userAuditLog.getActionedBy(), userAuditLog.getUserCode(),
                    userAuditLog.getProcessName(), userAuditLog.getDbTable(), userAuditLog.getDbActionSig(),
                    userAuditLog.getActivityDetails());
    }

}
