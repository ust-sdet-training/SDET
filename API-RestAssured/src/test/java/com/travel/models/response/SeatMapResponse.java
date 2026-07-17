package com.travel.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.List;

public record SeatMapResponse(

        @JsonProperty("flight_id") String flightId,

        String layout,

        List<String> cols,

        int total,

        int available,

        List<Row> rows

) {
}