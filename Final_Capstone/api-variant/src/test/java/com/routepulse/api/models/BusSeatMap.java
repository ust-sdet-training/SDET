package com.routepulse.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusSeatMap {
    private String busId;
    private String operator;
    private String layout;
    private Decks decks;

    public String getBusId() {
        return busId;
    }

    public Decks getDecks() {
        return decks;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Decks {
        private List<Seat> lower;
        private List<Seat> upper;

        public List<Seat> getLower() {
            return lower;
        }

        public List<Seat> getUpper() {
            return upper;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Seat {
        private String seatId;
        private String deck;
        private String state;

        public String getSeatId() {
            return seatId;
        }

        public String getState() {
            return state;
        }
    }
}
