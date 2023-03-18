package com.mycommonservref.api.common.commonservice.filter;

import com.mycommonservref.api.common.commonservice.util.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class LogFilterTest {

    private AutoCloseable closeable;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void test_REGEX_FOR_PASS_THROUGH_URLS_in_ITS_USERS_URL_TRUE_1() {
        String inputURL = "/portal-internal/api/case-user/v1/its-users/Richard.Trent@lpma.nsw.gov.au";

        if (inputURL.matches(Constants.REGEX_FOR_PASS_THROUGH_URLS)) {
            assert true;
        } else {
            assert false;
        }
    }


    @Test
    void test_REGEX_FOR_PASS_THROUGH_URLS_in_ITS_USERS_URL_TRUE_2() {
        String inputURL = "/portal-internal/api/case-user/v1/its-users/R";

        if (inputURL.matches(Constants.REGEX_FOR_PASS_THROUGH_URLS)) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    void test_REGEX_FOR_PASS_THROUGH_URLS_in_ITS_USERS_URL_TRUE_3() {
        String inputURL = "/portal-internal/api/case-user/v1/its-users/Rddd";

        if (inputURL.matches(Constants.REGEX_FOR_PASS_THROUGH_URLS)) {
            assert true;
        } else {
            assert false;
        }
    }

    @Test
    void test_REGEX_FOR_PASS_THROUGH_URLS_in_ITS_USERS_URL_FALSE_1() {
        String inputURL = "/portal-internal/api/case-user/v1/its-users";

        if (inputURL.matches(Constants.REGEX_FOR_PASS_THROUGH_URLS)) {
            assert false;
        } else {
            assert true;
        }
    }

    @Test
    void test_REGEX_FOR_PASS_THROUGH_URLS_in_ITS_USERS_URL_FALSE_2() {
        String inputURL = "/portal-internal/api/case-user/v1/its/R";

        if (inputURL.matches(Constants.REGEX_FOR_PASS_THROUGH_URLS)) {
            assert false;
        } else {
            assert true;
        }
    }


    @Test
    void test_REGEX_FOR_PASS_THROUGH_URLS_in_ACTUATOR_HEALTH_URL_TRUE_1() {
        String inputURL = "portal-internal/api/case-packet/actuator/health";

        if (inputURL.matches(Constants.REGEX_FOR_PASS_THROUGH_URLS)) {
            assert true;
        } else {
            assert false;
        }
    }


    @Test
    void test_REGEX_FOR_PASS_THROUGH_URLS_in_ACTUATOR_HEALTH_URL_FALSE_2() {
        String inputURL = "portal-internal/api/case-packet/not/this/me";

        if (inputURL.matches(Constants.REGEX_FOR_PASS_THROUGH_URLS)) {
            assert false;
        } else {
            assert true;
        }
    }

}
