package com.tripstack.model.request;

public class FlightSearchRequest {

    private String from;
    private String to;
    private String date;
    private int passengers;
    private String travelClass;

    public FlightSearchRequest() {
    }

    public FlightSearchRequest(String from,
                               String to,
                               String date,
                               int passengers,
                               String travelClass) {

        this.from = from;
        this.to = to;
        this.date = date;
        this.passengers = passengers;
        this.travelClass = travelClass;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

}