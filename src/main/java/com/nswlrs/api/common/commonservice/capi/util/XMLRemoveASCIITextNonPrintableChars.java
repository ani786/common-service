package com.nswlrs.api.common.commonservice.capi.util;

/**
 * The type Escape chars.
 */
public class XMLRemoveASCIITextNonPrintableChars {

    /**
     * replace All string.
     * <p>
     * replace &
     * replace quotation
     * replace apostrophe
     * ---------------------------
     * strips off all non-ASCII characters
     * erases all the ASCII control characters
     * removes non-printable characters from Unicode
     * ------------------------------
     *
     * @param s the s
     * @return the string
     */
    public static String replaceAll(String s) {
        return s.replaceAll("&(?!amp;)", "&amp;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;")
                    .replaceAll("[^\\x00-\\x7F]", "").replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "").replaceAll("\\p{C}", "")
                    .trim();
    }
}
