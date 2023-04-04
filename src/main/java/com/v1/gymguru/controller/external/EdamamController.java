package com.v1.gymguru.controller.external;

import com.v1.gymguru.edamam.EdamamMealDto;
import com.v1.gymguru.edamam.EdamamClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/gymguru/edamam")
@RequiredArgsConstructor
public class EdamamController {
    private final EdamamClient edamamClient;

    @GetMapping(value = "/meals/{mealName}")
    public ResponseEntity<List<EdamamMealDto>> getEdamamMeals(@PathVariable String mealName) {
        return ResponseEntity.ok(edamamClient.getEdamamMeals(mealName));
    }
}
