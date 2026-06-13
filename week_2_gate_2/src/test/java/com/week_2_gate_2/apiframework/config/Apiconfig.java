package com.week_2_gate_2.apiframework.config;

public class Apiconfig {
static public final String BASE_URL = System.getProperty(
        "baseUri",
        System.getenv().getOrDefault(null, "http://localhost:4000")
    );
    
}