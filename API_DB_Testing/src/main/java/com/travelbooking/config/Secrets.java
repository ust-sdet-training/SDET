package com.travelbooking.config;

public final class Secrets {

    private Secrets() {
    }

    public static final String EMAIL =
            Config.get("SAITEJA_TRIPSTACK_USERNAME");

    public static final String PASSWORD =
            Config.get("SAITEJA_TRIPSTACK_PASSWORD");

}