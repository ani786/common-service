package com.nswlrs.api.common.commonservice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.nswlrs.api.common.commonservice.model.UserAuditLog;
import com.nswlrs.api.common.commonservice.repository.UserAuditLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * The type User audit log repository test.
 */
class UserAuditLogRepositoryTest {

    @InjectMocks
    private UserAuditLogRepository userAuditLogRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test get user audit log id.
     */
    @Test
    void testGetUserAuditLogId() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(1);
        assertTrue(userAuditLogRepository
                .insert(UserAuditLog.builder().actionDate("10-Sep-02 14:10:10.123000").actionedBy("integration test")
                        .userCode("jdoe").processName("case-Queue").dbTable("USER_QUEUE").dbActionSig("I")
                        .activityDetails("WATER,SCAN").build()) >= 0);
    }
}
