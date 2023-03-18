package com.mycommonservref.api.common.commonservice.capi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mycommonservref.api.common.commonservice.capi.model.ApiResultPage;
import com.mycommonservref.api.common.commonservice.capi.model.ApiResultPages;
import com.mycommonservref.api.common.commonservice.util.Constants;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiResultPagesFormatter {

    public ApiResultPages formatSearchResult(String searchResult) throws Exception {
        List<String> resultList = Arrays.asList(searchResult.split("\\|"));
        if (resultList.get(Constants.STATUS_CODE_INDEX).equals(Constants.STATUS_CODE_SUCCESS_M)) {
            return formatMessages(resultList);
        }
        else if (resultList.get(Constants.STATUS_CODE_INDEX).equals(Constants.STATUS_CODE_SUCCESS_Q)) {
            return formatMessages(resultList);
        } else if (resultList.get(Constants.STATUS_CODE_INDEX).equals(Constants.STATUS_CODE_SUCCESS_R)) {
            return formatMessages(resultList);
        } else if (resultList.get(Constants.STATUS_CODE_INDEX).equals(Constants.STATUS_CODE_SUCCESS_S)) {
            return formatPages(resultList);
        } else if (resultList.get(Constants.STATUS_CODE_INDEX).equals(Constants.STATUS_CODE_UN_SUCCESS)) {
            throw new Exception(resultList.get(Constants.ERROR_MESSAGE_U_INDEX));
        } else if (resultList.get(Constants.STATUS_CODE_INDEX).equals(Constants.STATUS_CODE_M)) {
            throw new Exception(formatErrorMessage(resultList));
        } else {
            throw new Exception();
        }
    }

    private String formatErrorMessage(List<String> resultList) {
        StringBuilder output = new StringBuilder();
        int lineCount = 0;
        int contentPosition = Constants.TOTAL_ERROR_LINE_NUMBER_INDEX + 1;
        int lineCheck = 1;
        String errorFlag = "N";
        while ((lineCount < Integer.parseInt(resultList.get(Constants.TOTAL_ERROR_LINE_NUMBER_INDEX))) &&
                    errorFlag.equals("N")) {
            if (resultList.get(contentPosition).equals(Constants.END_OF_CONTENT)) {
                errorFlag = "Y";
                output.append("Unexpected end-of-data<br>lineCount=").append(lineCount).append(" contentPosition=")
                            .append(contentPosition).append(" content=").append(resultList.get(contentPosition));
            } else {
                lineCount = Integer.parseInt(resultList.get(contentPosition));
                if (lineCount != lineCheck) {
                    errorFlag = "Y";
                    output.append("Data Loss During Transmission2<br>lineCount=").append(lineCount)
                                .append(" contentPosition=")
                                .append(contentPosition).append(" content=").append(resultList.get(contentPosition));
                } else {
                    contentPosition++;
                    lineCheck++;
                    output.append(resultList.get(contentPosition)).append("<br>\n");
                    contentPosition++;
                }
            }
        }
        return output.toString();
    }

    private ApiResultPages formatMessages(List<String> resultList) {
        ApiResultPage page = ApiResultPage.builder().build();
        ApiResultPages pages = ApiResultPages.builder().build();
        page.setLines(resultList);
        pages.addPage(page);
        return pages;
    }

    private ApiResultPages formatPages(List<String> resultList) {
        ApiResultPage page = ApiResultPage.builder().build();
        ApiResultPages pages = ApiResultPages.builder().build();

        List<String> lines = new ArrayList<>();

        int lineCount = 0;
        int contentPosition = Constants.TOTAL_LINE_NUMBER_INDEX + 1;
        int lineCheck = 1;
        String errorFlag = "N";
        while ((lineCount < Integer.parseInt(resultList.get(Constants.TOTAL_LINE_NUMBER_INDEX))) &&
                    errorFlag.equals("N")) {
            if (resultList.get(contentPosition).equals(Constants.PAGE_BREAK)) {
                page.setLines(lines);
                pages.addPage(page);

                page = ApiResultPage.builder().build();
                lines = new ArrayList<>();

                contentPosition++;
            } else if (resultList.get(contentPosition).equals(Constants.END_OF_CONTENT)) {
                errorFlag = "Y";
            } else {
                lineCount = Integer.parseInt(resultList.get(contentPosition));
                if (lineCount != lineCheck) {
                    errorFlag = "Y";
                } else {
                    contentPosition++;
                    lineCheck++;
                    lines.add(resultList.get(contentPosition));
                    contentPosition++;
                }
            }
        }

        page.setLines(lines);
        pages.addPage(page);

        return pages;
    }
}
