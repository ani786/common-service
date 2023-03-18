package com.mycommonservref.api.common.commonservice.capi.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.ToString;

/**
 * The type Web api.
 */
@JacksonXmlRootElement(localName = "webapi")
@ToString
public class WebApi {

    /**
     * The Status.
     */
    public int status;
    /**
     * The Charge.
     */
    public String charge;
    /**
     * The Badfield.
     */
    public int badfield;

    /**
     * The Data.
     */
    @JacksonXmlProperty(localName = "data")
    public Data data;
}
