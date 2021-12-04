package com.nswlrs.api.common.commonservice.external.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class LogUtils {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String convertJsonObjectToString(Object jsonObject) {
        try {
            return objectMapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            log.info("the json processing exception: {} ", e.getMessage());
        }
        return "";
    }
}
