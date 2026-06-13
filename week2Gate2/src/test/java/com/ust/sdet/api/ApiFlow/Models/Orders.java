package com.ust.sdet.api.ApiFlow.Models;

@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public record Orders(
        String orderNumber,
        int userId,
        String status,
        String payment
) {}
