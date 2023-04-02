package com.v1.gymguru.edamam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EdamamRecipeDto {
    @JsonProperty("recipe")
    private EdamamMealDto edamamMealDto;
}
