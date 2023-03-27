package com.gymguru.controller.external;

import com.gymguru.domain.dto.external.EdamamMealDto;
import com.gymguru.edamam.EdamamClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/edamam")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EdamamController {
    private final EdamamClient edamamClient;

    @GetMapping(value = "meals/{mealName}")
    public ResponseEntity<List<EdamamMealDto>> getEdamamMeals(@PathVariable String mealName) {
        return ResponseEntity.ok(edamamClient.getEdamamMeals(mealName));
    }
}
