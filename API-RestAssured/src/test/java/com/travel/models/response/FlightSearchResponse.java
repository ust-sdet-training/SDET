package com.travel.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FlightSearchResponse(String from, String to, String date, int count, List<Flight> flights) { }