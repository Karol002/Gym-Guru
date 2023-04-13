package com.v1.gymguru.external.api.edamam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EdamamHitDto {

    @JsonProperty("hits")
    private List<EdamamRecipeDto> edamamRecipeDto;
}
