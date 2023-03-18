package com.mycommonservref.api.common.commonservice.external.utils;

import java.util.Optional;

import com.mycommonservref.api.common.commonservice.external.input.ActivityLogInput;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

/**
 * The type Activity log utils.
 */
@UtilityClass
public class ActivityLogUtils {
    private static final String UNKNOWN = "Unknown";
    private static final String NOT_APPLICABLE = "N/A";

    /**
     * Populate activity log input activity log input.
     *
     * @param operationType  the operation type
     * @param dbActionType   the db action type
     * @param tableType      the table type
     * @param recordId       the record id
     * @param payload        the payload
     * @param additionalInfo the additional info
     * @param errorType      the error type
     * @param status         the status
     * @return the activity log input
     */
    public ActivityLogInput populateActivityLogInput(Optional<OperationType> operationType,
                                                     Optional<DBActionType> dbActionType,
                                                     Optional<TableType> tableType, Optional<Integer> recordId,
                                                     Optional<String> payload,
                                                     Optional<String> additionalInfo, Optional<ErrorType> errorType,
                                                     Optional<HttpStatus> status) {

        return ActivityLogInput.builder()
                    .apiOperation(operationType.orElse(() -> UNKNOWN).getOperation())
                    .dbAction(dbActionType.orElse(DBActionType.UNKNOWN).getAction())
                    .tableName(tableType.orElse(() -> UNKNOWN).getTableName())
                    .recordId(recordId.orElse(-1))
                    .inputData(payload.orElse(NOT_APPLICABLE))
                    .additionalInformation(additionalInfo.orElse(NOT_APPLICABLE))
                    .errorType(errorType.orElse(() -> 1).getId())
                    .httpStatus(status.orElse(HttpStatus.INTERNAL_SERVER_ERROR).value())
                    .build();
    }
}
