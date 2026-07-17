package com.ust.sdet.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingRetrieveResponse {
    private String pnr;

    @JsonProperty("state")
    private String status;
    private String flightId;
    private String[] seatIds;
    private String passengerName;
    private String passengerEmail;
    private Integer totalFarePaise;

    public String getPnr() {
        return pnr;
    }

    public String getStatus() {
        return status;
    }

}
