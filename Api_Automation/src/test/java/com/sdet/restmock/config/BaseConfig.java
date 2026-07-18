package com.sdet.restmock.config;

import io.github.cdimascio.dotenv.Dotenv;

public class BaseConfig {

    public static final Dotenv dotenv = Dotenv.load();

    public static final String BASE_URL =
            dotenv.get("BASE_URL");

    public static final String TEST_USERNAME =
            dotenv.get("TEST_USERNAME");

    public static final String PASSWORD =
            dotenv.get("PASSWORD");


}