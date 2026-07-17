package com.ust.capstone.api.model;

public record Bus(
        String id,
        String operator,
        String operatorName,
        String kind,
        String origin,
        String dest,
        String depTime,
        String arrTime,
        String baseFarePaise,
        String taxPaise,
        String farePaise,
        int seatsLeft

) {
}