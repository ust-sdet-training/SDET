package com.api_testing.support;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    private static final Dotenv dotenv = Dotenv.load();

    public static final String BASE_URL = dotenv.get("BASE_URL", "https://restful-booker.herokuapp.com");
    public static final String USER_NAME = dotenv.get("USER_NAME", "admin");
    public static final String PASSWORD = dotenv.get("PASSWORD", "password123");

    public static final String WRONG_PASSWORD = dotenv.get("WRONG_PASSWORD", "wrongPassword");

    public static final String BASE_PATH = dotenv.get("BASE_PATH","/booking");
    public static final String AUTH_PATH = dotenv.get("AUTH_PATH","/auth");

}

