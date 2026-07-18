package com.week7.finalgate.API.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightSearchResponse {

    private String from;

    private String to;

    private String date;

    private int pax;

    @JsonProperty("class")
    private String flightClass;

    private int count;

    private List<Flight> flights;

    public FlightSearchResponse() {}

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDate() {
        return date;
    }

    public int getPax() {
        return pax;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public int getCount() {
        return count;
    }

    public List<Flight> getFlights() {
        return flights;
    }
}