package com.routepulse.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Bus {
    private String id;
    private String operator;
    private String operatorName;
    private String kind;
    private String origin;
    private String dest;
    private String depTime;
    private String arrTime;
    private Integer baseFarePaise;
    private Integer taxPaise;
    private Integer farePaise;
    private Integer seatsLeft;

    public String getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public Integer getFarePaise() {
        return farePaise;
    }

    public Integer getSeatsLeft() {
        return seatsLeft;
    }
}
