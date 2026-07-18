package com.week7.finalgate.API.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class SeatRow {

    private int row;

    private boolean exit_row;

    private List<Seat> seats;

    public SeatRow() {
    }

    public List<Seat> getSeats() {
        return seats;
    }
}