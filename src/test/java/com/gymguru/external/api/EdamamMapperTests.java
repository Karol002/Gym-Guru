package com.gymguru.external.api;

import com.gymguru.external.api.edamam.EdamamMapper;
import com.gymguru.external.api.edamam.EdamamMeal;
import com.gymguru.external.api.edamam.dto.EdamamHitDto;
import com.gymguru.external.api.edamam.dto.EdamamMealDto;
import com.gymguru.external.api.edamam.dto.EdamamRecipeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EdamamMapperTests {

    @Autowired
    private EdamamMapper edamamMapper;

    @Test
    void testMapToEdamamMealList() {
        //Given
        EdamamMealDto edamamMealDto1 = new EdamamMealDto("Label 1", List.of("Ingredient 1.1", "Ingredient 1.2"));
        EdamamMealDto edamamMealDto2 = new EdamamMealDto("Label 2", List.of("Ingredient 2.1", "Ingredient 2.2"));
        EdamamRecipeDto edamamRecipeDto1 = new EdamamRecipeDto(edamamMealDto1);
        EdamamRecipeDto edamamRecipeDto2 = new EdamamRecipeDto(edamamMealDto2);
        List<EdamamRecipeDto> edamamRecipeDtos = List.of(edamamRecipeDto1, edamamRecipeDto2);
        EdamamHitDto edamamHitDto = new EdamamHitDto(edamamRecipeDtos);

        //When
        List<EdamamMeal> edamamMeals = edamamMapper.mapToEdamamMealList(edamamHitDto);

        //Then
        assertEquals(2, edamamMeals.size());
        assertEquals("Label 1", edamamMeals.get(0).getLabel());
        assertEquals("Ingredient 1.1, Ingredient 1.2", edamamMeals.get(0).getIngredientLines());
        assertEquals("Label 2", edamamMeals.get(1).getLabel());
        assertEquals("Ingredient 2.1, Ingredient 2.2", edamamMeals.get(1).getIngredientLines());
    }
}
