package com.apitesting.data.model;

import java.util.List;
import java.util.Map;

public class ApiModels {

    public static class LoginResponse {
        public String token;
        public String empId;
        public String role;
        public String displayName;
    }

    public static class Booking {
        public String id;
        public String pnr;
        public String empId;
        public String journeyType;
        public String inventoryId;
        public String state;
        public List<String> seatIds;
        public Long amountPaise;
        public Boolean refundable;
        public String holdExpiresAt;
    }

    public static class BusSearchResponse {
        public String from;
        public String to;
        public String date;
        public Integer count;
        public List<Bus> buses;

        public static class Bus {
            public String id;
            public String operator;
            public String operatorName;
            public String kind;
            public String origin;
            public String dest;
            public String depTime;
            public String arrTime;
            public Long baseFarePaise;
            public Long taxPaise;
            public Long farePaise;
            public Integer seatsLeft;
        }
    }

    public static class BusSeatMap {
        public String busId;
        public String operator;
        public String layout;
        public Map<String, List<DeckSeat>> decks;

        public static class DeckSeat {
            public String seatId;
            public String deck;
            public String kind;
            public String state;
        }
    }

    public static class SimpleError {
        public String error;
        public String message;
        public String state;
    }
}
