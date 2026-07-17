package com.week7.finalgate.API.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Flight {

    private String id;

    @JsonProperty("airline_code")
    private String airlineCode;

    @JsonProperty("airline_name")
    private String airlineName;

    @JsonProperty("flight_no")
    private String flightNo;

    private String origin;

    private String dest;

    @JsonProperty("dep_time")
    private String departureTime;

    @JsonProperty("arr_time")
    private String arrivalTime;

    @JsonProperty("total_paise")
    private int totalPaise;

    public Flight() {
    }

    public String getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDest() {
        return dest;
    }

    public int getTotalPaise() {
        return totalPaise;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getFlightNo() {
        return flightNo;
    }
}