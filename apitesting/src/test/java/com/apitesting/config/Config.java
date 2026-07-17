package com.apitesting.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    private Config() {}

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private static final String APIBASEURL =
            dotenv.get("APIBASEURL", "https://api.tripstack.doomple.com/");

    public static String APIBASEURL() {
        return APIBASEURL;
    }
}