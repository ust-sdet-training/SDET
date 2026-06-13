package com.ust.sdet.api.ApiFlow.Models;

@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public record Product(
        int id,
        String name,
        String category,
        int price,
        int stock
) {}
