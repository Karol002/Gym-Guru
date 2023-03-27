package com.v1.gymguru.domain.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EdamamMealDto {
    @JsonProperty("label")
    private String label;
    @JsonProperty("ingredientLines")
    private List<String> ingredientLines;
}
