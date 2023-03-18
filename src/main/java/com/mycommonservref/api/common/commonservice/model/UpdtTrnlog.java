package com.mycommonservref.api.common.commonservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

/**
 * The type Updt trnlog.
 */
@Getter
@Jacksonized
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@With
public class UpdtTrnlog {

    private final String transnDate;

    private final String transnType;

    private final String userCode;

    private final String progName;

    private final String inputData;

    private final String recordKey;

    private final String dbTble;
    private final String dbActionSig;
    private final String aftrImage;

}
