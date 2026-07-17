package com.apitesting.data;

import io.github.cdimascio.dotenv.Dotenv;

public class testUser {

    private testUser() {}

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private static final String EMAIL =
            dotenv.get("TRIPSTACK_EMAIL", "");

    private static final String PASSWORD =
            dotenv.get("TRIPSTACK_PASSWORD", "");

    public static String EMAIL() {
        return EMAIL;
    }

    public static String PASSWORD() {
        return PASSWORD;
    }
}
