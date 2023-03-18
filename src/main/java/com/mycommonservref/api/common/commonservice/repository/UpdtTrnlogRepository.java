package com.mycommonservref.api.common.commonservice.repository;

import com.mycommonservref.api.common.commonservice.model.UpdtTrnlog;
import com.mycommonservref.api.common.commonservice.repository.statement.UpdtTrnlogStatement;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The type Updt trnlog repository.
 */
@Slf4j
@Repository
@Builder
@Getter
@With
@RequiredArgsConstructor
public class UpdtTrnlogRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Insert integer.
     *
     * @param updtTrnlog the updt trnlog
     * @return the integer
     */
    public Integer insert(UpdtTrnlog updtTrnlog) {
        log.info("insert into the UpdtTrnl log with the user code: {} ", updtTrnlog.getUserCode());
        return jdbcTemplate.update(UpdtTrnlogStatement.INSERT_INTO_UPDATE_TRN_LOG, updtTrnlog.getTransnDate(),
                    updtTrnlog.getTransnType(), updtTrnlog.getUserCode(), updtTrnlog.getProgName(),
                    updtTrnlog.getInputData(), updtTrnlog.getRecordKey(),
                    updtTrnlog.getDbTble(), updtTrnlog.getDbActionSig(), updtTrnlog.getAftrImage());
    }

}
