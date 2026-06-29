package com.ust.sdet.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record postModel(
        int userId,
        int id,
        String title,
        String body
) {

}
