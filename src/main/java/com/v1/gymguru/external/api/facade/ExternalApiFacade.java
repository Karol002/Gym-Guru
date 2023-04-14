package com.v1.gymguru.external.api.facade;

import com.v1.gymguru.external.api.edamam.EdamamClient;
import com.v1.gymguru.external.api.edamam.EdamamMapper;
import com.v1.gymguru.external.api.edamam.EdamamMeal;
import com.v1.gymguru.external.api.openai.OpenAIRequest;
import com.v1.gymguru.external.api.openai.OpenAiClient;
import com.v1.gymguru.external.api.openai.OpenAiMapper;
import com.v1.gymguru.external.api.openai.OpenAiMessage;
import com.v1.gymguru.external.api.openai.dto.OpenAiObjectDto;
import com.v1.gymguru.external.api.wger.WgerCategory;
import com.v1.gymguru.external.api.wger.WgerClient;
import com.v1.gymguru.external.api.wger.WgerExercise;
import com.v1.gymguru.external.api.wger.WgerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalApiFacade {
    private final OpenAiClient openAiClient;
    private final OpenAiMapper openAiMapper;
    private final WgerClient wgerClient;
    private final WgerMapper wgerMapper;
    private final EdamamClient edamamClient;
    private final EdamamMapper edamamMapper;

    public OpenAiMessage getOpenAiResponse(OpenAiMessage openAiMessage) {
        OpenAIRequest openAIRequest = openAiMapper.mapToOpenAiRequest(openAiMessage);
        OpenAiObjectDto objectDto = openAiClient.getOpenAiRequest(openAIRequest);
        return openAiMapper.mapToOpenAiMessageDto(objectDto);
    }

    public List<WgerExercise> getWgerExercisesByCategoy(Long id) {
        return wgerMapper.mapToWgerExerciseDtos(wgerClient.getWgerExercisesByCategoy(id));
    }

    public List<WgerCategory> getWgerCategories() {
        return wgerMapper.mapToWgerCategoryDtos(wgerClient.getWgerCategories());
    }

    public List<EdamamMeal> getEdamamMeals(String mealName) {
        return edamamMapper.mapToEdamamMeal(edamamClient.getEdamamMeals(mealName));
    }
}