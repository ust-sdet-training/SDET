package com.ust.sdet.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusSearchResponse {
    private String from;
    private String to;
    private String date;
    private Integer count;
    private List<Bus> buses;

    public Integer getCount() {
        return count;
    }

    public List<Bus> getBuses() {
        return buses;
    }
}
