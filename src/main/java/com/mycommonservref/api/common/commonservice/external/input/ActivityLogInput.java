package com.mycommonservref.api.common.commonservice.external.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Jacksonized
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class ActivityLogInput {
    private final String apiOperation;
    private final String dbAction;
    private final String tableName;
    private final String additionalInformation;
    private final String inputData;
    private final Integer recordId;
    private final Integer errorType;
    private final Integer httpStatus;
    private final String logType;

}
