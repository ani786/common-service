package com.mycommonservref.api.common.commonservice.external.config;

import java.io.IOException;

import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final StringDecoder stringDecoder = new StringDecoder();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (HttpStatus.resolve(response.status()) != null) {
            throw new ResponseStatusException(HttpStatus.resolve(response.status()),
                    getErrorMessage(methodKey, response));
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, getErrorMessage(methodKey, response));
    }

    private String getErrorMessage(String methodKey, Response response) {
        String message = "";
        if (response.body() != null) {
            try {
                message = stringDecoder.decode(response, String.class).toString();
            } catch (IOException e) {
                log.error("Error Deserializing response body from failed feign request response; {} and stacktrace {} ",
                        methodKey, e);
            }
        }
        return message;
    }
}
