package com.v1.gymguru.external.api.edamam;

import com.v1.gymguru.external.api.edamam.dto.EdamamHitDto;
import com.v1.gymguru.external.api.edamam.dto.EdamamMealDto;
import com.v1.gymguru.external.api.edamam.dto.EdamamRecipeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EdamamMapper {
    private List<EdamamMealDto> mapToEdamamMealDtos(EdamamHitDto edamamHitDto) {
        return Optional.ofNullable(edamamHitDto)
                .map(EdamamHitDto::getEdamamRecipeDto)
                .stream()
                .flatMap(List::stream)
                .map(EdamamRecipeDto::getEdamamMealDto)
                .collect(Collectors.toList());
    }

    public List<EdamamMeal> mapToEdamamMeal(EdamamHitDto edamamHitDto) {
        List<EdamamMealDto> edamamMealDtos = mapToEdamamMealDtos(edamamHitDto);
        return edamamMealDtos.stream()
                .map(edamamMealDto -> new EdamamMeal(edamamMealDto.getLabel(),
                        String.join(", ", edamamMealDto.getIngredientLines())))
                .collect(Collectors.toList());
    }
}
