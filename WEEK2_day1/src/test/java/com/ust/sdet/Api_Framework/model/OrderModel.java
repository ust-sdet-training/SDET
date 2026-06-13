package com.ust.sdet.Api_Framework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderModel(
        String orderNumber,
        int userId,
        String status,
        String payment
) {}

