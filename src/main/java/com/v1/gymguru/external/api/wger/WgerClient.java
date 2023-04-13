package com.v1.gymguru.external.api.wger;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WgerClient {
    private final RestTemplate restTemplate;
    private final WgerConfiguration wgerConfiguration;

    public List<WgerExerciseDto> getWgerExercisesByCategoy(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(wgerConfiguration.getWgerApiEndpoint()+ "/exercise?")
                .queryParam("language", wgerConfiguration.getWgerApiLanguage())
                .queryParam("category", id)
                .build()
                .encode()
                .toUri();

        WgerExerciseBoardDto mainResponse = restTemplate.getForObject(url, WgerExerciseBoardDto.class);

        return mapToWgerExerciseDtos(mainResponse);
    }

    public List<WgerCategoryDto> getWgerCategories() {
        URI url = UriComponentsBuilder.fromHttpUrl(wgerConfiguration.getWgerApiEndpoint()+ "/exercisecategory")
                .build()
                .encode()
                .toUri();
        WgerCategoryBoardDto mainResponse = restTemplate.getForObject(url, WgerCategoryBoardDto.class);

        return mapToWgerCategoryDtos(mainResponse);
    }

    private List<WgerExerciseDto> mapToWgerExerciseDtos(WgerExerciseBoardDto wgerExerciseBoardDto) {
        return new ArrayList<>(Optional.ofNullable(wgerExerciseBoardDto)
                .map(WgerExerciseBoardDto::getWgerExerciseDtos)
                .orElse(Collections.emptyList()))
                .stream()
                .peek(dto -> dto.setDescription(removeHtmlTags(dto.getDescription())))
                .collect(Collectors.toList());
    }

    private List<WgerCategoryDto> mapToWgerCategoryDtos(WgerCategoryBoardDto wgerCategoryBoardDto) {
        return new ArrayList<>(Optional.ofNullable(wgerCategoryBoardDto)
                .map(WgerCategoryBoardDto::getWgerCategoryDtos)
                .orElse(Collections.emptyList()));
    }

    private String removeHtmlTags(String text) {
        return Jsoup.parse(text).text();
    }
}
