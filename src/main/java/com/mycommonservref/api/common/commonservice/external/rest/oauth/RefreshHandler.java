package com.mycommonservref.api.common.commonservice.external.rest.oauth;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class RefreshHandler {

    @Value("${security.permissions}")
    private String allowedPermissions;

}
