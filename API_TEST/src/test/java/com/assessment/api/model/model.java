package com.assessment.api.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record model(
        Long id,
        Long userId,
        String title,
        String body) {

}
