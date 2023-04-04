package com.v1.gymguru.controller.external;

import com.v1.gymguru.wger.WgerCategoryDto;
import com.v1.gymguru.wger.WgerExerciseDto;
import com.v1.gymguru.wger.WgerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/gymguru/wger")
@RequiredArgsConstructor
public class WgerController {
    private final WgerClient wgerClient;

    @GetMapping(value = "/exercises/{categoryId}")
    public ResponseEntity<List<WgerExerciseDto>> getWgerExercisesByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(wgerClient.getWgerExercisesByCategoy(categoryId));
    }

    @GetMapping(value = "/categories")
    public ResponseEntity<List<WgerCategoryDto>> getWgerExercisesByCategoryId() {
        return ResponseEntity.ok(wgerClient.getWgerCategories());
    }
}
