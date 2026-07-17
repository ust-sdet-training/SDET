package com.travel.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Flight(

        String id,

        @JsonProperty("airline_code") String airlineCode,

        @JsonProperty("airline_name") String airlineName,

        @JsonProperty("flight_no") String flightNo,

        String origin,

        String dest,

        @JsonProperty("dep_time") String depTime,

        @JsonProperty("arr_time") String arrTime,

        @JsonProperty("base_fare_paise") int baseFarePaise,

        @JsonProperty("tax_paise") int taxPaise,

        @JsonProperty("total_paise") int totalPaise,

        @JsonProperty("fare_display") String fareDisplay

) {
}