package com.gymguru.wger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class WgerConfiguration {
    @Value("${wger.api.endpoint.prod}")
    private String wgerApiEndpoint;
    @Value("${wger.app.key}")
    private String wgerAppKey;
    @Value("${wger.api.language}")
    private String wgerApiLanguage;
}
