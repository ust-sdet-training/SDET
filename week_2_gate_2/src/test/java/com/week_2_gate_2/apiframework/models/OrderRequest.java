package com.week_2_gate_2.apiframework.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderRequest(List<Integer> items, String currency) {
}
