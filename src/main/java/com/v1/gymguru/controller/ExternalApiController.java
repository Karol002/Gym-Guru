package com.v1.gymguru.controller;

import com.v1.gymguru.external.api.edamam.EdamamMeal;
import com.v1.gymguru.external.api.openai.OpenAiMessage;
import com.v1.gymguru.external.api.facade.ExternalApiFacade;
import com.v1.gymguru.external.api.wger.WgerCategory;
import com.v1.gymguru.external.api.wger.WgerExercise;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/gymguru")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExternalApiController {
    private final ExternalApiFacade externalApiFacade;

    @PostMapping(value = "/openai", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OpenAiMessage> generateResponse(@RequestBody OpenAiMessage openAiMessage) {
        return ResponseEntity.ok(externalApiFacade.getOpenAiResponse(openAiMessage));
    }

    @GetMapping(value = "/edamam/meals/{mealName}")
    public ResponseEntity<List<EdamamMeal>> getEdamamMeals(@PathVariable String mealName) {
        return ResponseEntity.ok(externalApiFacade.getEdamamMeals(mealName));
    }

    @GetMapping(value = "/wger/exercises/{categoryId}")
    public ResponseEntity<List<WgerExercise>> getWgerExercisesByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(externalApiFacade.getWgerExercisesByCategoy(categoryId));
    }

    @GetMapping(value = "/wger/categories")
    public ResponseEntity<List<WgerCategory>> getWgerExercisesByCategoryId() {
        return ResponseEntity.ok(externalApiFacade.getWgerCategories());
    }
}
