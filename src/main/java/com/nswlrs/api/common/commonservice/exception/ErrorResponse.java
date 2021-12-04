package com.nswlrs.api.common.commonservice.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Error response.
 */
@Data
@Slf4j
@ToString
public class ErrorResponse {
    private int statusCode;
    private String errorDescription;
    private String exceptionMessage;
    private String exceptionClassName;
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

    /**
     * Instantiates a new Error response.
     *
     * @param httpStatus         the http status
     * @param errorDescription   the error description
     * @param exceptionMessage   the exception message
     * @param exceptionClassName the exception class name
     */
    public ErrorResponse(int httpStatus, String errorDescription, String exceptionMessage, String exceptionClassName) {
        this.statusCode = httpStatus;
        this.errorDescription = errorDescription;
        this.exceptionMessage = exceptionMessage;
        this.exceptionClassName = exceptionClassName;

    }
}
