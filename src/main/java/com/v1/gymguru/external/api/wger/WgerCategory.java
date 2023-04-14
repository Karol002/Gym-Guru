package com.v1.gymguru.external.api.wger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WgerCategory {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
}
