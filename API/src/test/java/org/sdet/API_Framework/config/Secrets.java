package org.sdet.API_Framework.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class Secrets {

    private static final Dotenv dotenv =
            Dotenv.configure()
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .load();

    private Secrets() {
    }

    public static final String BASE_URL =
            dotenv.get("BASE_URL");

    public static final String EMAIL =
            dotenv.get("EMAIL");

    public static final String PASSWORD =
            dotenv.get("PASSWORD");

    public static final String DB_URL =
            dotenv.get("DB_URL");

    public static final String DB_USERNAME =
            dotenv.get("DB_USERNAME");

    public static final String DB_PASSWORD =
            dotenv.get("DB_PASSWORD");

}