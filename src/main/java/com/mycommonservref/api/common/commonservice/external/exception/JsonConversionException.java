package com.mycommonservref.api.common.commonservice.external.exception;

/**
 * Exception during the json conversion.
 */
public class JsonConversionException extends RuntimeException {

    public JsonConversionException(String message) {
        super(message);
    }
}
