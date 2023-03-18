package com.mycommonservref.api.common.commonservice.util;

import java.util.Set;

public enum Constants {
    ;
    /* common */
    public static final String X_ACTIONED_BY = "X-Actioned-By";
    public static final String X_CORRELATION_ID = "X-Correlation-Id";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ITS_USERS = "its_users";
    public static final String REGEX_FOR_PASS_THROUGH_URLS = "^(?=.*its-users/)(.*)$|^(?=.*actuator/health)(.*)$";
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String IS_BLANK = "is blank";
    public static final String INSERT = "I";
    public static final String UPDATE = "U";
    public static final String DELETE = "D";
    public static final String COLON = ":";
    public static final String SEMI_COLON = ";";

    /* token permissions*/

    public static final String AUTH_TOKEN_ISSUE_MESSAGE = "The request lacks valid authentication credentials";
    public static final String PERMISSION_MAPPING = "permission_mapping";
    public static final String END_POINT = "end_point";
    public static final String META_DATA = "meta_data";

    public static final String SERVLET_NAME = "servlet_name";
    public static final String ATTRIBUTES = "attributes";
    public static final String PERMISSIONS = "permissions";
    public static final String ACTION = "action";

    /* Exception handling */

    public static final String CLIENT_ERROR_BAD_REQUEST = "Client Error Bad Request";
    public static final String CLIENT_ERROR_BAD_REQUEST_IOEXCEPTION = "Client Error Bad Request IOException";
    public static final String CLIENT_ERROR_INCORRECT_KEY_OR_VALUE_IN_REQUEST =
            "Client Error incorrect Key or value in request";
    public static final String CLIENT_ERROR_REQUEST_NOT_READABLE = "Client Error Request Body not readable";
    public static final String MISSING_REQUEST_HEADER = "Constraint Violation Missing Request Header";
    public static final String CONSTRAINT_VIOLATION_INCORRECT_VALUE_IN_REQUEST =
            "Constraint Violation incorrect value in request";
    public static final String MISSING_INPUT_DATA_IN_REQUEST =
            "Constraint Violation Missing input key or value in request";
    public static final String CLIENT_ERROR_FORBIDDEN = "Client Error Forbidden";
    public static final String CLIENT_ERROR_NOT_FOUND = "Client Error Not Found";
    public static final String CLIENT_ERROR_HTTP_REQUEST_METHOD_NOT_SUPPORTED =
            "Client Error HTTP Request Method Not supported";
    public static final String UNPROCESSABLE_ENTITY = "Application Validation Failure";
    public static final String SQL_EXCEPTION = "SQL Exception";
    public static final String UNKNOWN_ERROR = "Unknown Error";

    public static final String NULL_POINTER_EXCEPTION = "NullPointerException";
    public static final String HTTP_MESSAGE_NOT_READABLE_EXCEPTION = "HttpMessageNotReadableException";
    public static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "MethodArgumentNotValidException";
    public static final Set<String> APPLY_TO_EXTERNAL_URI =
            Set.of("/portal/api/workspace", "/portal/api/pdfgenerator", "/portal/api/user", "/portal/api/document",
                    "/portal/api/email", "/portal/api/logging", "/portal/api/lodgingparty", "/portal/api/sapconnect",
                    "/portal/api/itsconnect");
    public static final String VERSION2 = "v2";

    /* Case Queue */
    public static final String CASE_QUEUE = "case-queue";
    public static final String USER_QUEUE = "user_queue";
    public static final String QUEUES = "QUEUES : ";
    public static final String QUEUE_CODES_NULL = "Delete operation only, no Queue codes inserted";

    /* C-API */
    public static final String STATUS_CODE_SUCCESS_M = "M"; // "api_request": none
    public static final String STATUS_CODE_SUCCESS_Q = "Q"; // "api_request": "DS|CT|5942-199"
    public static final String STATUS_CODE_SUCCESS_R = "R"; // "api_request": "DR|lrs:null-ewang|CT|12345-123|||E||"
    public static final String STATUS_CODE_SUCCESS_S = "S"; // "api_request": "RD|lrs:DRD-aelali|1/23424"

    public static final int STATUS_CODE_INDEX = 0;
    public static final String STATUS_CODE_UN_SUCCESS = "U";
    public static final int ERROR_MESSAGE_U_INDEX = 1;
    public static final String STATUS_CODE_M = "M";
    public static final int TOTAL_ERROR_LINE_NUMBER_INDEX = 1;
    public static final String END_OF_CONTENT = "E";
    public static final int TOTAL_LINE_NUMBER_INDEX = 6;
    public static final int TOTAL_LINE_NUMBER_INDEX_FOR_MESSAGES = 4;
    public static final String PAGE_BREAK = "P";
}
