package com.nswlrs.api.common.commonservice.capi.facade;

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
 * The type Linux web api executor.
 */
@Slf4j
@Component
public class LinuxWebApiExecutor extends WebApiExecutor {

    private String runnableScriptWithActualPath;

    /**
     * To construct the command line of the process.
     *
     * @param apiRequest the api request
     * @return Porcess
     * @throws IOException IOException
     */
    public Process createProcess(final WebApiRequest apiRequest) throws IOException {
        log.info("createProcess linux apiRequest {}", apiRequest);
        final String[] envs = new String[] {"API_CLIENT_CODE=" + apiRequest.getClientCode(),
                    "API_PORT_NUMBER=" + apiRequest.getPortNumber(),
                    "REMOTE_USER=" + apiRequest.getRemoteUser(), "API_REQUEST=" + apiRequest.getApiRequest()};
        log.info("createProcess linux envs: {}, runnableScript: {}", envs, runnableScriptWithActualPath);
        final Process process =
                    Runtime.getRuntime().exec(new String[] {"/bin/bash", "-c", runnableScriptWithActualPath}, envs);
        log.info(" process {}", process);
        return process;
    }

    @PostConstruct
    private void setConfig() {
        scheduledExecutorService = Executors.newScheduledThreadPool(threadPool);
        timeLimiter = TimeLimiter.of(Duration.ofSeconds(timeout));
        try {
            File resource = new ClassPathResource(runnableScript).getFile();
            log.info("runnableScript {}", runnableScript);
            runnableScriptWithActualPath = resource.toPath().toString();
            log.info("runnableScriptWithActualPath {}", runnableScriptWithActualPath);
        } catch (IOException ex) {
            log.error("Exception when getting the location of webapi ", ex.getMessage());
        }
    }
}
