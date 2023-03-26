package com.gymguru.wger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WgerClient {
    private final RestTemplate restTemplate;
    private final WgerConfiguration wgerConfiguration;
}
