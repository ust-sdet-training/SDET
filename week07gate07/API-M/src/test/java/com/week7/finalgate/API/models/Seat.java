package com.week7.finalgate.API.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Seat {

    @JsonProperty("seat_id")
    private String seatId;

    private int row;

    private String col;

    private boolean occupied;

    private boolean window;

    private boolean aisle;

    private boolean middle;

    public Seat() {
    }

    public String getSeatId() {
        return seatId;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean isWindow() {
        return window;
    }

    public boolean isAisle() {
        return aisle;
    }

    public boolean isMiddle() {
        return middle;
    }
}