package com.ust.sdet.support;

import io.github.cdimascio.dotenv.Dotenv;

public final class Config {

    private static final Dotenv dotenv =
            Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

    private Config() {
    }

    public static String baseUrl() {

        return dotenv
                .get("BASE_URL")
                .replaceAll("/$", "");
    }

    public static String catalogUrl() {

        return baseUrl() + "/catalog";
    }

    public static String loginUrl() {

        return baseUrl() + "/login";
    }

    public static boolean headless() {

        return Boolean.parseBoolean(
                dotenv.get("HEADLESS", "true")
        );
    }

    public static boolean headed() {

        return !headless();
    }
}