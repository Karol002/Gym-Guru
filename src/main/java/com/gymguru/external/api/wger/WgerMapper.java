package com.gymguru.external.api.wger;

import com.gymguru.external.api.wger.dto.WgerCategoryBoardDto;
import com.gymguru.external.api.wger.dto.WgerExerciseBoardDto;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WgerMapper {
    public List<WgerExercise> mapToWgerExerciseDtoList(WgerExerciseBoardDto wgerExerciseBoardDto) {
        return new ArrayList<>(Optional.ofNullable(wgerExerciseBoardDto)
                .map(WgerExerciseBoardDto::getWgerExercises)
                .orElse(Collections.emptyList()))
                .stream()
                .peek(dto -> dto.setDescription(removeHtmlTags(dto.getDescription())))
                .collect(Collectors.toList());
    }

    public List<WgerCategory> mapToWgerCategoryDtoList(WgerCategoryBoardDto wgerCategoryBoardDto) {
        return new ArrayList<>(Optional.ofNullable(wgerCategoryBoardDto)
                .map(WgerCategoryBoardDto::getWgerCategories)
                .orElse(Collections.emptyList()));
    }

    private String removeHtmlTags(String text) {
        return Jsoup.parse(text).text();
    }
}
