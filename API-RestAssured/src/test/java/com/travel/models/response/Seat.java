package com.travel.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Seat(

        @JsonProperty("seat_id") String seatId,

        int row,

        String col,

        boolean window,

        boolean aisle,

        boolean middle,

        @JsonProperty("exit_row") boolean exitRow,

        boolean occupied

) { }