package com.gymguru.external.api.edamam;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EdamamConfig {
    @Value("${edaman.api.endpoint.prod}")
    private String edamamApiEndpoint;
    @Value("${edamam.app.id}")
    private String edamamAppId;
    @Value("${edamam.app.key}")
    private String edamamAppKey;
}
