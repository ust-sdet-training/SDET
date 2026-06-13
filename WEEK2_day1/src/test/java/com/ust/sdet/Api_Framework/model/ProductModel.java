package com.ust.sdet.Api_Framework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductModel (
        int id,
        String name,
        String category,
        BigDecimal price,
        int stock
){}
