package com.gymguru.external.api.edamam;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EdamamMeal {
    private String label;
    private String ingredientLines;
}
