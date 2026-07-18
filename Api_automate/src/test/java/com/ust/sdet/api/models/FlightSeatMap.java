package com.ust.sdet.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightSeatMap {
    @JsonProperty("flight_id")
    private String flightId;

    private List<CabinRow> rows;

    public String getFlightId() {
        return flightId;
    }

    public List<CabinRow> getRows() {
        return rows;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CabinRow {
        private Integer row;
        private List<CabinSeat> seats;

        public Integer getRow() {
            return row;
        }

        public List<CabinSeat> getSeats() {
            return seats;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CabinSeat {
        @JsonProperty("seat_id")
        private String seatId;

        private Boolean occupied;

        public String getSeatId() {
            return seatId;
        }

        public Boolean getOccupied() {
            return occupied;
        }
    }
}
