package com.ust.sdet.api.apiframework.datamodel;

@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public record Order (
        String orderNumber,
        int userId,
        String status,
        String payment
) {

}





