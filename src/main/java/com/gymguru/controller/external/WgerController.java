package com.gymguru.controller.external;

import com.gymguru.domain.dto.external.EdamamMealDto;
import com.gymguru.domain.dto.external.WgerCategoryDto;
import com.gymguru.domain.dto.external.WgerExerciseDto;
import com.gymguru.wger.WgerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/wger")
@RequiredArgsConstructor
@CrossOrigin("*")
public class WgerController {
    private final WgerClient wgerClient;

    @GetMapping(value = "exercises/{categoryId}")
    public ResponseEntity<List<WgerExerciseDto>> getWgerExercisesByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(wgerClient.getWgerExercisesByCategoy(categoryId));
    }

    @GetMapping(value = "categories")
    public ResponseEntity<List<WgerCategoryDto>> getWgerExercisesByCategoryId() {
        return ResponseEntity.ok(wgerClient.getWgerCategories());
    }
}
