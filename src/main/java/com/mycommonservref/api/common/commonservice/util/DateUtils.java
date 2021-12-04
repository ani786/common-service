package com.nswlrs.api.common.commonservice.util;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ObjectUtils;

/**
 * The type Date utils.
 */
@UtilityClass
public class DateUtils {

    /**
     * The Date time formatter.
     */
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Convert timestamp to string string.
     *
     * @param timestamp the timestamp
     * @return the string
     */
    public static String convertTimestampToString(final Timestamp timestamp) {
        return ObjectUtils.isNotEmpty(timestamp) ?
                    OffsetDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault()).format(dateTimeFormatter) :
                    null;
    }

}
