package com.nswlrs.api.common.commonservice.external.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorType implements ErrorType {
    NOT_APPLICABLE(0),
    UNEXPECTED(1),
    FORBIDDEN(2),
    BAD_REQUEST(4),

    ITS_UNEXPECTED(5000),
    ITS_FORBIDDEN(5001),
    ITS_BAD_REQUEST(5002);
    private final Integer id;
}
