package com.nswlrs.api.common.commonservice.external.utils;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nswlrs.api.common.commonservice.external.exception.JsonConversionException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class to convert the json string to Map.
 */
@Slf4j
@UtilityClass
public class JsonUtils {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * <p>
     * Convert String to Map.
     * </p>
     *
     * @param jsonString json string
     * @return Map corresponding to the json string
     * @throws JsonConversionException JsonConversionException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertStringToMap(final String jsonString) throws JsonConversionException {
        try {
            return mapper.readValue(jsonString, Map.class);
        } catch (JsonProcessingException ex) {
            log.error("The response mapping have issue: {} and reason: {} ", jsonString, ex);
            throw new JsonConversionException("The response mapping have issue");
        } catch (Exception e) {
            log.error("The unexpect mapping issue: {} and reason: {} ", jsonString, e);
            throw new JsonConversionException("The unexpect mapping issue");
        }

    }
}
