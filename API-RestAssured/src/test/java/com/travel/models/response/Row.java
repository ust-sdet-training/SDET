package com.travel.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Row(

        int row,

        @JsonProperty("exit_row") boolean exitRow,

        List<Seat> seats

) { }