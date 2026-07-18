package com.apitesting.data;

import io.github.cdimascio.dotenv.Dotenv;

public class testUser {

    private testUser() {}

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private static final String EMAIL = dotenv.get("TRIPSTACK_EMAIL", "");

    private static final String PASSWORD = dotenv.get("TRIPSTACK_PASSWORD", "");
    private static final String DB_USER = dotenv.get("DB_USER","");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD","");

    public static String DB_USER() {
        return DB_USER;
    }
    public static String DB_PASSWORD() {
        return DB_PASSWORD;
    }
    public static String EMAIL() {
        return EMAIL;
    }

    public static String PASSWORD() {
        return PASSWORD;
    }
}
