package com.apimocktest.models;

import static com.apimocktest.config.ApiConfig.*;

import java.util.Map;

public class Data {
    public static Map<String,String> PostOrders = Map.of("name",NAME,"email",EMAIL,"role",ROLE);
    public static Map<String,String> PutOrders = Map.of("name",NAME,"email",EMAIL,"role",ROLE);

} 