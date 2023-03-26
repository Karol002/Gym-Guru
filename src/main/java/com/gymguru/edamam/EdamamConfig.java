package com.gymguru.edamam;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Getter
public class EdamamConfig {
    @Value("${edaman.api.endpoint.prod}")
    private String edamamApiEndpoint;
    @Value("${edamam.app.id}")
    private String edamamAppId;
    @Value("${edamam.app.key}")
    private String edamamAppKey;
}
