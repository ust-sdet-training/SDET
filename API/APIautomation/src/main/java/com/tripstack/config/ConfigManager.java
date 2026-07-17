package com.tripstack.config;

public class ConfigManager {

    public static final String BASE_URL =
            "https://tripstack.doomple.com";

    public static final String EMAIL =
            "niaj@tripstack.test";

    public static final String PASSWORD =
            "Password@123";

    public static final String EMP_ID =
            "1014";

    public static String get(String key) {

        switch (key) {

            case "base.url":
                return BASE_URL;

            case "email":
                return EMAIL;

            case "password":
                return PASSWORD;

            case "emp.id":
                return EMP_ID;

            default:
                throw new RuntimeException(
                        "Invalid property : " + key
                );
        }
    }
}