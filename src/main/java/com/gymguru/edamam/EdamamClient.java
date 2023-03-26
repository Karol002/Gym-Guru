package com.gymguru.edamam;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class EdamamClient {
    private final RestTemplate restTemplate;
    private final EdamamConfig edamamConfig;
}
