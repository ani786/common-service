package com.nswlrs.api.common.commonservice.external.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DBActionType {
    UPDATE("U"),
    INSERT("I"),
    DELETE("D"),
    SELECT("R"),
    UNKNOWN("X");
    private final String action;
}
