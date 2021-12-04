package com.nswlrs.api.common.commonservice.capi.model;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class ApiResultPage implements Serializable {
    private List<String> lines;

    public String getPageContent() {
        StringBuilder sb = new StringBuilder();

        if ("M".equals(lines.get(0))) {
            sb.append(lines.get(3)).append("\n");
        } else {
            for (String line : lines) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }
}
