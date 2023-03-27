package com.gymguru.domain.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gymguru.domain.dto.external.EdamamMealDto;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EdamamRecipeDto {

    @JsonProperty("recipe")
    private EdamamMealDto edamamMealDto;
}
