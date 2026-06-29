package com.ust.sdet.accountWorks.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentCheck {
    private static final Dotenv dotEnv = Dotenv.load();

    public static final String BASE_URL = dotEnv.get("base_url", "https://jsonplaceholder.typicode.com");

}
