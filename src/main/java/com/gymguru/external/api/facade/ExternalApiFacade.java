package com.gymguru.external.api.facade;

import com.gymguru.external.api.edamam.EdamamClient;
import com.gymguru.external.api.edamam.EdamamMapper;
import com.gymguru.external.api.edamam.EdamamMeal;
import com.gymguru.external.api.openai.*;
import com.gymguru.external.api.openai.dto.OpenAiBoardDto;
import com.gymguru.external.api.wger.WgerCategory;
import com.gymguru.external.api.wger.WgerClient;
import com.gymguru.external.api.wger.WgerExercise;
import com.gymguru.external.api.wger.WgerMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalApiFacade {
    private final Logger logger = LoggerFactory.getLogger(ExternalApiFacade.class);
    private final OpenAiClient openAiClient;
    private final OpenAiMapper openAiMapper;
    private final WgerClient wgerClient;
    private final WgerMapper wgerMapper;
    private final EdamamClient edamamClient;
    private final EdamamMapper edamamMapper;

    public OpenAiMessage getOpenAiResponse(OpenAiMessage openAiMessage) {
        openAiMessage.setContent(OpenAiConfiguration.VIRTUAL_TRAINER_INSTRUCTION + openAiMessage.getContent());
        OpenAIRequest openAIRequest = openAiMapper.mapToOpenAiRequest(openAiMessage);

        try {
            OpenAiBoardDto objectDto = openAiClient.getOpenAiRequest(openAIRequest);
            return openAiMapper.mapToOpenAiMessageDto(objectDto);
        } catch (HttpClientErrorException exception) {
            logger.warn("OpenAi exception: " + exception.getMessage());
            return new OpenAiMessage("Error OpenAi api");
        }
    }

    public List<WgerExercise> getWgerExercisesByCategory(Long id) {
        try {
            return wgerMapper.mapToWgerExerciseDtoList(wgerClient.getWgerExercisesByCategoy(id));
        } catch (HttpClientErrorException exception) {
            logger.warn("Wger exception: " + exception.getMessage());
            return Collections.emptyList();
        }
    }

    public List<WgerCategory> getWgerCategories() {
        try {
            return wgerMapper.mapToWgerCategoryDtoList(wgerClient.getWgerCategories());
        } catch (HttpClientErrorException exception) {
            logger.warn("Wger exception: " + exception.getMessage());
            return Collections.emptyList();
        }
    }

    public List<EdamamMeal> getEdamamMeals(String mealName) {
        try {
            return edamamMapper.mapToEdamamMealList(edamamClient.getEdamamMeals(mealName));
        } catch (HttpClientErrorException exception) {
            logger.warn("Edamam exception: " + exception.getMessage());
            return Collections.emptyList();
        }
    }
}