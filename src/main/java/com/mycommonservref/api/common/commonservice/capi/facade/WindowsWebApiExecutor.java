package com.mycommonservref.api.common.commonservice.capi.facade;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;

import io.github.resilience4j.timelimiter.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * The type Windows web api executor.
 */
@Slf4j
@Component
public class WindowsWebApiExecutor extends WebApiExecutor {

    /**
     * To construct the command line of the process.
     *
     * @param apiRequest the api request
     * @return Porcess
     * @throws IOException IOException
     */
    public Process createProcess(final WebApiRequest apiRequest) throws IOException {
        log.info("createProcess windows apiRequest {}", apiRequest);
        StringBuilder builder = new StringBuilder();
        builder.append("cmd /c set API_CLIENT_CODE=").append(apiRequest.getClientCode())
                    .append("&& set API_PORT_NUMBER=").append(apiRequest.getPortNumber())
                    .append("&& set REMOTE_USER=").append(apiRequest.getRemoteUser())
                    .append("&& set API_REQUEST=")
                    .append(apiRequest.getApiRequest().replace("|", "^|")
                                .replace("<", "^<").replace(">", "^>")).append("&& ")
                    .append(runnableScript);
        log.info("the runnableScript is: {} ", runnableScript);

        final Process process = Runtime.getRuntime().exec(builder.toString());
        log.info(" process {}", process);
        return process;
    }

    @PostConstruct
    private void setConfig() {
        scheduledExecutorService = Executors.newScheduledThreadPool(threadPool);
        timeLimiter = TimeLimiter.of(Duration.ofSeconds(timeout));
        try {
            File resource = new ClassPathResource("capi/webapi.exe").getFile();
            runnableScript = resource.toPath().toString();
        } catch (IOException ex) {
            log.error("Exception when getting the location of webapi ", ex.getMessage());
        }
    }
}
