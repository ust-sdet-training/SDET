package com.week7.finalgate.API.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class SeatMapResponse {

    private String flight_id;

    private String layout;

    private int total;

    private int available;

    private List<SeatRow> rows;

    public SeatMapResponse() {
    }

    public List<SeatRow> getRows() {
        return rows;
    }

    public int getAvailable() {
        return available;
    }
}
