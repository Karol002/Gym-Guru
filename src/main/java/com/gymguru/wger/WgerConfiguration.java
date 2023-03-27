package com.gymguru.wger;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Getter
@Configuration
public class WgerConfiguration {
    @Value("${wger.api.endpoint.prod}")
    private String wgerApiEndpoint;
    @Value("${wger.api.language}")
    private String wgerApiLanguage;
}
