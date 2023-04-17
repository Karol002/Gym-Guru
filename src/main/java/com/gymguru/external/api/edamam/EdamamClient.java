package com.gymguru.external.api.edamam;

import com.gymguru.external.api.edamam.dto.EdamamHitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class EdamamClient {
    private final RestTemplate restTemplate;
    private final EdamamConfig edamamConfig;

    public EdamamHitDto getEdamamMeals(String mealName) {
        URI url = UriComponentsBuilder.fromHttpUrl(edamamConfig.getEdamamApiEndpoint() + "/search?")
                .queryParam("q", mealName)
                .queryParam("app_id", edamamConfig.getEdamamAppId())
                .queryParam("app_key", edamamConfig.getEdamamAppKey())
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(url, EdamamHitDto.class);
    }

}
