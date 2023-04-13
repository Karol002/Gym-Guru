package com.v1.gymguru.external.api.edamam;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EdamamMeal {
    private String label;
    private String ingredientLines;
}
