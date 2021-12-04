package com.nswlrs.api.common.commonservice.capi.util;

import lombok.experimental.UtilityClass;

/**
 * The type System utils.
 */
@UtilityClass
public class SystemUtils {

    /**
     * Is windows boolean.
     *
     * @return the boolean
     */
    public static Boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }
}
