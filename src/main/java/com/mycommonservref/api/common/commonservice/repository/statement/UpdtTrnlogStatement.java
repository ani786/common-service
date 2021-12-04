package com.nswlrs.api.common.commonservice.repository.statement;

/**
 * The type Updt trnlog statement.
 */
public class UpdtTrnlogStatement {

    /**
     * The constant INSERT_INTO_UPDATE_TRN_LOG.
     */
    public static final String INSERT_INTO_UPDATE_TRN_LOG = """                
                INSERT INTO UPDT_TRNLOG(TRANSN_DATE, TRANSN_TYPE, USER_CODE, PROG_NAME, INPUT_DATA, RECORD_KEY, LTO_SECTION, TBLE_NUM, DB_TBLE, DB_ACTION_SIG, AFTR_IMAGE)
                                             VALUES(TO_DATE(SUBSTR(?,1,20), 'DD-MON-RR HH24.MI.SS'), ?, ?, ?, ?, ?, NULL, 0, ?, ?, ?)
                                             """;

}
