package com.nswlrs.api.common.commonservice.exception;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;

import com.nswlrs.api.common.commonservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * The type Common exception handler.
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    /**
     * Bad request error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    public ErrorResponse badRequest(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.BAD_REQUEST.value(), Constants.CLIENT_ERROR_BAD_REQUEST);
    }

    /**
     * Bad request io exception error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    public ErrorResponse badRequestIOException(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.BAD_REQUEST.value(),
                    Constants.CLIENT_ERROR_BAD_REQUEST_IOEXCEPTION);
    }

    /**
     * Method not allowed error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    public ErrorResponse methodArgumentNotValid(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.BAD_REQUEST.value(),
                    Constants.CLIENT_ERROR_INCORRECT_KEY_OR_VALUE_IN_REQUEST);
    }

    /**
     * Sql exception error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    public ErrorResponse httpMessageNotReadableException(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.BAD_REQUEST.value(),
                    Constants.CLIENT_ERROR_REQUEST_NOT_READABLE);
    }

    /**
     * Missing request header exception error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    public ErrorResponse missingRequestHeaderException(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.BAD_REQUEST.value(), Constants.MISSING_REQUEST_HEADER);
    }

    /**
     * Constraint violation exception error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    public ErrorResponse constraintViolationException(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.BAD_REQUEST.value(),
                    Constants.CONSTRAINT_VIOLATION_INCORRECT_VALUE_IN_REQUEST);
    }

    /**
     * Constraint violation exception error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  //400
    public ErrorResponse httpRequestMethodArgumentTypeMismatchException(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.BAD_REQUEST.value(),
                    Constants.CONSTRAINT_VIOLATION_INCORRECT_VALUE_IN_REQUEST);
    }

    /**
     * Constraint violation exception error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  //400
    public ErrorResponse httpRequestUnexpectedTypeException(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.BAD_REQUEST.value(),
                    Constants.CONSTRAINT_VIOLATION_INCORRECT_VALUE_IN_REQUEST);
    }

    /**
     * Forbidden exception error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)//403
    public ErrorResponse forbiddenException(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.FORBIDDEN.value(), Constants.CLIENT_ERROR_FORBIDDEN);
    }

    /**
     * Not found error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)//404
    public ErrorResponse notFound(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.NOT_FOUND.value(), Constants.CLIENT_ERROR_NOT_FOUND);
    }

    /**
     * Not found error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)//405
    public ErrorResponse httpRequestMethodNotSupportedException(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.METHOD_NOT_ALLOWED.value(),
                    Constants.CLIENT_ERROR_HTTP_REQUEST_METHOD_NOT_SUPPORTED);
    }

    /**
     * Not found error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(HttpClientErrorException.UnprocessableEntity.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)//422
    public ErrorResponse httpUnprocessableEntityException(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    Constants.UNPROCESSABLE_ENTITY);
    }


    /**
     * Sql exception error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(UncategorizedSQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//500
    public ErrorResponse uncategorizedSQLException(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.SQL_EXCEPTION);
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class, SQLException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  //500
    public ErrorResponse invalidDataAccessApiUsageException(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.SQL_EXCEPTION);
    }

    /**
     * Run time error response.
     *
     * @param ex      the ex
     * @param request the request
     * @return the error response
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//500
    public ErrorResponse runTime(Exception ex, HttpServletRequest request) {
        return getErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.UNKNOWN_ERROR);
    }

    private ErrorResponse getErrorResponse(final Exception ex, final HttpServletRequest request, final int httpStatus,
                                           final String errorDescription) {
        String actionedBy = request.getHeader(Constants.X_ACTIONED_BY);
        actionedBy = StringUtils.isEmpty(actionedBy) ? Constants.IS_BLANK : actionedBy;
        ErrorResponse response =
                    new ErrorResponse(httpStatus, errorDescription,
                                StringUtils.substringAfterLast(ex.getMessage(), Constants.COLON),
                                ex.getClass().getSimpleName());

        if (httpStatus >= 500 && Constants.NULL_POINTER_EXCEPTION.equals(ex.getClass().getSimpleName())) {
            response.setExceptionMessage(ex.getMessage());
        }

        //these are all grouped for the < 500
        if (httpStatus < 500) {
            response.setExceptionMessage(ex.getMessage());
        }
        //specialised case 1 for < 500 ,  //specialised case 2 for < 500
        if (httpStatus == 400 && Constants.HTTP_MESSAGE_NOT_READABLE_EXCEPTION.equals(ex.getClass().getSimpleName())) {
            response.setExceptionMessage(StringUtils.substringBefore(ex.getMessage(), Constants.COLON));
        } else if (httpStatus == 400 &&
                    Constants.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.equals(ex.getClass().getSimpleName())) {
            response.setExceptionMessage(StringUtils.removeEnd(StringUtils.removeStart(StringUtils.substringAfterLast(ex.getMessage(), "default message")," ["),"]] ").trim());
        }

        log.error("user: {} with exception: {} ", actionedBy,
                    new ErrorResponse(httpStatus, errorDescription, ex.getMessage(), ex.getClass().getSimpleName()));
        return response;
    }

}
