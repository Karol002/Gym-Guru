package com.gymguru.external.api.facade;


import com.gymguru.external.api.edamam.EdamamClient;
import com.gymguru.external.api.edamam.EdamamMapper;
import com.gymguru.external.api.edamam.EdamamMeal;
import com.gymguru.external.api.edamam.dto.EdamamHitDto;
import com.gymguru.external.api.edamam.dto.EdamamMealDto;
import com.gymguru.external.api.edamam.dto.EdamamRecipeDto;
import com.gymguru.external.api.openai.*;
import com.gymguru.external.api.openai.dto.OpenAiDetailsDto;
import com.gymguru.external.api.openai.dto.OpenAiBoardDto;
import com.gymguru.external.api.openai.dto.OpenAiResponseDto;
import com.gymguru.external.api.wger.WgerCategory;
import com.gymguru.external.api.wger.WgerClient;
import com.gymguru.external.api.wger.WgerExercise;
import com.gymguru.external.api.wger.WgerMapper;
import com.gymguru.external.api.wger.dto.WgerCategoryBoardDto;
import com.gymguru.external.api.wger.dto.WgerExerciseBoardDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExternalApiFacadeTest {

    @InjectMocks
    private ExternalApiFacade externalApiFacade;

    @Mock
    private EdamamClient edamamClient;

    @Mock
    private EdamamMapper edamamMapper;

    @Mock
    private WgerClient wgerClient;

    @Mock
    private WgerMapper wgerMapper;

    @Mock
    private OpenAiClient openAiClient;

    @Mock
    private OpenAiMapper openAiMapper;

    @Test
    void testGetEdamamMeals() {
        //Given
        String mealName = "chicken";
        EdamamHitDto edamamHitDto = new EdamamHitDto();
        List<EdamamMealDto> edamamMealDtos = new ArrayList<>();
        edamamMealDtos.add(new EdamamMealDto("Meal 1", List.of("Ingredient 1.1", "Ingredient 1.2")));
        edamamMealDtos.add(new EdamamMealDto("Meal 2", List.of("Ingredient 2.1", "Ingredient 2.2")));
        edamamHitDto.setEdamamRecipeDto(Collections.singletonList(new EdamamRecipeDto(new EdamamMealDto())));
        when(edamamClient.getEdamamMeals(mealName)).thenReturn(edamamHitDto);
        when(edamamMapper.mapToEdamamMealList(edamamHitDto)).thenReturn(
                edamamMealDtos.stream()
                        .map(edamamMealDto -> new EdamamMeal(edamamMealDto.getLabel(),
                                String.join(", ", edamamMealDto.getIngredientLines())))
                        .collect(Collectors.toList()));

        //When
        List<EdamamMeal> edamamMeals = externalApiFacade.getEdamamMeals(mealName);

        //Then
        assertNotNull(edamamMeals);
        assertEquals(2, edamamMeals.size());
        assertEquals("Meal 1", edamamMeals.get(0).getLabel());
        assertEquals("Ingredient 1.1, Ingredient 1.2", edamamMeals.get(0).getIngredientLines());
        assertEquals("Meal 2", edamamMeals.get(1).getLabel());
        assertEquals("Ingredient 2.1, Ingredient 2.2", edamamMeals.get(1).getIngredientLines());
        verify(edamamClient, times(1)).getEdamamMeals(mealName);
        verify(edamamMapper, times(1)).mapToEdamamMealList(edamamHitDto);
    }

    @Test
    void testGetWgerExercisesByCategoy() {
        //Given
        Long categoryId = 1L;
        List<WgerExercise> wgerExerciseList = new ArrayList<>();
        wgerExerciseList.add(new WgerExercise("Exercise 1", "Description 1"));
        wgerExerciseList.add(new WgerExercise("Exercise 2", "Description 2"));

        WgerExerciseBoardDto wgerExerciseBoardDto = new WgerExerciseBoardDto();
        wgerExerciseBoardDto.setWgerExercises(wgerExerciseList);

        List<WgerExercise> mappedWgerExercises = new ArrayList<>();
        mappedWgerExercises.add(new WgerExercise("Exercise 1", "Description 1"));
        mappedWgerExercises.add(new WgerExercise("Exercise 2", "Description 2"));

        when(wgerClient.getWgerExercisesByCategoy(categoryId)).thenReturn(wgerExerciseBoardDto);
        when(wgerMapper.mapToWgerExerciseDtoList(wgerExerciseBoardDto)).thenReturn(mappedWgerExercises);

        //When
        List<WgerExercise> result = externalApiFacade.getWgerExercisesByCategory(categoryId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("Exercise 1");
        assertThat(result.get(1).getDescription()).isEqualTo("Description 2");
    }

    @Test
    void testGetWgerCategories() {
        //Given
        List<WgerCategory> wgerCategoryList = new ArrayList<>();
        wgerCategoryList.add(new WgerCategory(1L, "Category 1"));
        wgerCategoryList.add(new WgerCategory(2L, "Category 2"));

        WgerCategoryBoardDto wgerCategoryBoardDto = new WgerCategoryBoardDto();
        wgerCategoryBoardDto.setWgerCategories(wgerCategoryList);

        List<WgerCategory> mappedWgerCategories = new ArrayList<>();
        mappedWgerCategories.add(new WgerCategory(1L, "Category 1"));
        mappedWgerCategories.add(new WgerCategory(2L, "Category 2"));

        when(wgerClient.getWgerCategories()).thenReturn(wgerCategoryBoardDto);
        when(wgerMapper.mapToWgerCategoryDtoList(wgerCategoryBoardDto)).thenReturn(mappedWgerCategories);

        //When
        List<WgerCategory> result = externalApiFacade.getWgerCategories();

        //Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getName()).isEqualTo("Category 2");
    }

    @Test
    public void testGetOpenAiResponse() {
        //Given
        String content = "Hello, how are you?";
        OpenAiMessage openAiMessage = new OpenAiMessage(content);
        List<OpenAiDetailsDto> messages = new ArrayList<>();
        messages.add(new OpenAiDetailsDto("sender", openAiMessage.getContent()));

        OpenAIRequest openAIRequest = new OpenAIRequest("model", messages, 0.0, 10L);
        OpenAiBoardDto openAiBoardDto = new OpenAiBoardDto();
        OpenAiResponseDto openAiResponseDto = new OpenAiResponseDto(openAiMessage);

        List<OpenAiResponseDto> openAiResponseDtos = new ArrayList<>();
        openAiResponseDtos.add(openAiResponseDto);
        openAiBoardDto.setChoices(openAiResponseDtos);

        when(openAiMapper.mapToOpenAiRequest(openAiMessage)).thenReturn(openAIRequest);
        when(openAiClient.getOpenAiRequest(openAIRequest)).thenReturn(openAiBoardDto);
        when(openAiMapper.mapToOpenAiMessageDto(openAiBoardDto)).thenReturn(openAiMessage);

        //When
        String expectedResult = OpenAiConfiguration.VIRTUAL_TRAINER_INSTRUCTION + content;
        OpenAiMessage response = externalApiFacade.getOpenAiResponse(openAiMessage);

        //Then
        assertEquals(expectedResult, response.getContent());
    }
}
