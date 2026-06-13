package com.ust.sdet.api.API_FrameWork.models;

@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public record Orders(
        String orderNumber,
        int userId,
        String status,
        String payment
) {}
