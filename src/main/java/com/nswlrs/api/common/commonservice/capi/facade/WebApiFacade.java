package com.nswlrs.api.common.commonservice.capi.facade;

import com.nswlrs.api.common.commonservice.capi.model.ApiResultPages;
import com.nswlrs.api.common.commonservice.capi.util.SystemUtils;
import com.nswlrs.api.common.commonservice.capi.util.XmlParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Web api facade.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebApiFacade {

    private final LinuxWebApiExecutor linuxWebApiExecutor;
    private final WindowsWebApiExecutor windowsWebApiExecutor;
    private final XmlParser xmlParser;

    public ApiResultPages runProcess(final WebApiRequest apiRequest) {
        String result;
        log.info("runProcess apiRequest {}", apiRequest);
        if (SystemUtils.isWindows()) {
            result = windowsWebApiExecutor.run(apiRequest);
            log.info("runProcess windows apiRequest {}", apiRequest);
        } else {
            log.info("runProcess linux apiRequest {}", apiRequest);
            result = linuxWebApiExecutor.run(apiRequest);
        }
        log.info("the result is : {} ", result);
        try {
            return xmlParser.parse(result);
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }
}
