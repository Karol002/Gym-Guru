package com.gymguru.external.api.wger;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class WgerConfiguration {
    @Value("${wger.api.endpoint.prod}")
    private String wgerApiEndpoint;
    @Value("${wger.api.language}")
    private String wgerApiLanguage;
}
