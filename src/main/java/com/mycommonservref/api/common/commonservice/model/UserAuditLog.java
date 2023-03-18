package com.mycommonservref.api.common.commonservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

/**
 * The type User audit log.
 */
@Getter
@Jacksonized
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@With
public class UserAuditLog {

    private final String actionDate;

    private final String actionedBy;

    private final String userCode;

    private final String processName;

    private final String dbTable;

    private final String dbActionSig;

    private final String activityDetails;
}
