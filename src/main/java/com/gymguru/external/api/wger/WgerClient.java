package com.gymguru.external.api.wger;

import com.gymguru.external.api.wger.dto.WgerCategoryBoardDto;
import com.gymguru.external.api.wger.dto.WgerExerciseBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class WgerClient {
    private final RestTemplate restTemplate;
    private final WgerConfiguration wgerConfiguration;

    public WgerExerciseBoardDto getWgerExercisesByCategoy(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(wgerConfiguration.getWgerApiEndpoint()+ "/exercise?")
                .queryParam("language", wgerConfiguration.getWgerApiLanguage())
                .queryParam("category", id)
                .build()
                .encode()
                .toUri();

        return  restTemplate.getForObject(url, WgerExerciseBoardDto.class);
    }

    public WgerCategoryBoardDto getWgerCategories() {
        URI url = UriComponentsBuilder.fromHttpUrl(wgerConfiguration.getWgerApiEndpoint()+ "/exercisecategory")
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(url, WgerCategoryBoardDto.class);
    }
}
