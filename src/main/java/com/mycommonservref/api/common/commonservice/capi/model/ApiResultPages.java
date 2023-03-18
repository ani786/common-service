package com.mycommonservref.api.common.commonservice.capi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

@Getter
@Builder
@With
@ToString
public class ApiResultPages implements Serializable {

    private final List<ApiResultPage> pages = new ArrayList<>();

    public void addPage(ApiResultPage page) {
        pages.add(page);
    }

    public int getPageCount() {
        return pages.size();
    }
}
