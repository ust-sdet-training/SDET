package com.travelbooking.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class Config {

    private static final Dotenv dotenv = Dotenv.load();

    public static final String BASE_URL = dotenv.get("BASE_URL");

    private Config() {
    }
}