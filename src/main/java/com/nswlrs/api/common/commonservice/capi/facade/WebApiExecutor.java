package com.nswlrs.api.common.commonservice.capi.facade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import io.github.resilience4j.timelimiter.TimeLimiter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * The type Web api executor.
 */
@Data
@Slf4j
public abstract class WebApiExecutor {

    /**
     * The Runnable script.
     */
    @Value("${api.c-api.runnableScript:run}")
    protected String runnableScript;

    /**
     * The Timeout.
     */
    @Value("${api.c-api.connection.timeout}")
    protected Integer timeout;

    /**
     * The Thread pool.
     */
    @Value("${api.c-api.connection.threadpool}")
    protected Integer threadPool;

    /**
     * The Scheduled executor service.
     */
    protected ScheduledExecutorService scheduledExecutorService;
    /**
     * The Time limiter.
     */
    protected TimeLimiter timeLimiter;

    /**
     * Run string.
     *
     * @param apiRequest the api request
     * @return the string
     */
    public String run(WebApiRequest apiRequest) {
        Process process = null;
        log.info("run  apiRequest {}", apiRequest);
        try {
            process = createProcess(apiRequest);
            log.info("run createProcess completed apiRequest {}", apiRequest);
            return runProcess(process);
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        } finally {
            IOUtils.closeQuietly(process.getInputStream());
            IOUtils.closeQuietly(process.getOutputStream());
            IOUtils.closeQuietly(process.getErrorStream());
            process.destroy();
        }
    }

    /**
     * Create process process.
     *
     * @param apiRequest the api request
     * @return the process
     * @throws IOException the io exception
     */
    protected abstract Process createProcess(final WebApiRequest apiRequest) throws IOException;

    /**
     * Stream gobble string.
     *
     * @param stream the stream
     * @return the string
     */
    protected String streamGobble(InputStream stream) {
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(isr);
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex.toString());
        }
        return sb.toString();
    }

    /**
     * Run process string.
     *
     * @param process the process
     * @return the string
     * @throws Exception the exception
     */
    protected String runProcess(Process process) throws Exception {
        final Process process1 = process;
        log.info("run  process {}", process);
        String result = timeLimiter.executeFutureSupplier(
                    () -> CompletableFuture.supplyAsync(() -> streamGobble(process1.getInputStream())));

        log.info("run  process  result {}", result);
        return result;
    }
}
