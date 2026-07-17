package com.ust.sdet.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightSearchResponse {
    private String from;
    private String to;
    private String date;
    private Integer pax;
    private String clazz;
    private Integer count;
    private List<Flight> flights;

    public Integer getCount() {
        return count;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
