package com.apitesting.support;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    static Dotenv dotenv = Dotenv.load();

    private static String baseurl = dotenv.get("baseurl", "https://jsonplaceholder.typicode.com");

    public static String BASE_URL(){
        return baseurl;
    }
}   
