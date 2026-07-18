package com.ust.sdet.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Flight {
    private String id;
    private String airline_code;
    private String airline_name;
    private String flight_no;
    private String origin;
    private String dest;
    private String dep_time;
    private String arr_time;
    private Integer base_fare_paise;
    private Integer tax_paise;
    private Integer total_paise;
    private String fare_display;

    public String getId() {
        return id;
    }

    public String getAirlineName() {
        return airline_name;
    }

    public Integer getTotalPaise() {
        return total_paise;
    }
}
