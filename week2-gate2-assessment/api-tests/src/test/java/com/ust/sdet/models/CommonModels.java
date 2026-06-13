package com.ust.sdet.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public final class CommonModels {

    private CommonModels() {
    }


    @JsonIgnoreProperties(
            ignoreUnknown = true
    )
    public static class ErrorResponse {

        private String message;

        public String getMessage() {
            return message;
        }

    }


    @JsonIgnoreProperties(
            ignoreUnknown = true
    )
    public static class ConflictResponse {

        private String message;

        private String currentStatus;

        private String expectedStatus;

        public String getMessage() {
            return message;
        }

        public String getCurrentStatus() {
            return currentStatus;
        }

        public String getExpectedStatus() {
            return expectedStatus;
        }

    }

}