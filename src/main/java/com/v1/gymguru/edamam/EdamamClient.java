package com.v1.gymguru.edamam;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class EdamamClient {
    private final RestTemplate restTemplate;
    private final EdamamConfig edamamConfig;

    public List<EdamamMealDto> getEdamamMeals(String mealName) {
        URI url = UriComponentsBuilder.fromHttpUrl(edamamConfig.getEdamamApiEndpoint() + "/search?")
                .queryParam("q", mealName)
                .queryParam("app_id", edamamConfig.getEdamamAppId())
                .queryParam("app_key", edamamConfig.getEdamamAppKey())
                .build()
                .encode()
                .toUri();

        EdamamHitDto mainResponse = restTemplate.getForObject(url, EdamamHitDto.class);

        return mapToEdamamMealDtos(mainResponse);
    }

    private List<EdamamMealDto> mapToEdamamMealDtos(EdamamHitDto edamamHitDto) {
        return Optional.ofNullable(edamamHitDto)
                .map(EdamamHitDto::getEdamamRecipeDto)
                .stream()
                .flatMap(List::stream)
                .map(EdamamRecipeDto::getEdamamMealDto)
                .collect(Collectors.toList());
    }
}
