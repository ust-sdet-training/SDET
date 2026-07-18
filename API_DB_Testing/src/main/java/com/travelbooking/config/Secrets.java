package com.travelbooking.config;

public final class Secrets {

    private Secrets() {
    }

    public static final String EMAIL =
            Config.get("TRIPSTACK_USERNAME");

    public static final String PASSWORD =
            Config.get("TRIPSTACK_PASSWORD");

}