package com.ust.sdet.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingConfirmResponse {
    private String pnr;

    @JsonProperty("state")
    private String status;

    public String getPnr() {
        return pnr;
    }

    public String getStatus() {
        return status;
    }
}
