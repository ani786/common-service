package com.nswlrs.api.common.commonservice.capi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.nswlrs.api.common.commonservice.capi.model.ApiResultPages;
import com.nswlrs.api.common.commonservice.capi.model.WebApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * The type Xml parser.
 */
@Slf4j
@Service
public class XmlParser {

    /**
     * Parse web api.
     *
     * @param xml the xml
     * @return the web api
     * @throws JsonProcessingException the json processing exception
     */
    public ApiResultPages parse(String xml) throws Exception {
        String result;
        if (StringUtils.isEmpty(xml)) {
            log.info("the XML Empty : {} ", xml);
            return ApiResultPages.builder().build();
        } else if (xml.contains("error")) {
            result = skipErrorFromTheBeginning(xml);
            log.info("the XML contains error : {} ", result);
        } else {
            result = xml;
            log.info("the XML : {} ", result);
        }

        XmlMapper xmlMapper = new XmlMapper();
        result = XMLRemoveASCIITextNonPrintableChars.replaceAll(result);
        log.info("the XML after XMLRemoveASCIITextNonPrintableChars : {} ", result);
        WebApi webApi = xmlMapper.readValue(result, WebApi.class);
        return ApiResultPagesFormatter.formatSearchResult(webApi.data.response);
    }

    private String skipErrorFromTheBeginning(String xml) {
        StringBuilder sb = new StringBuilder();
        int i = xml.indexOf("<webapi>");
        int j = xml.indexOf("<status>");
        String s = xml.substring(0, i + 8);
        sb.append(s);
        sb.append(xml.substring(j));
        //log.info("skipErrorFromTheBeginning  string : {}", sb);
        return sb.toString();
    }
}
