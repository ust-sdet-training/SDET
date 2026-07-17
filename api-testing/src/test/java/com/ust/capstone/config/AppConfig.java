package com.ust.capstone.config;

import com.ust.capstone.data.secret.Secrets;

public final class AppConfig {

    private AppConfig() {
    }

    public static final String BASE_URL =
            Secrets.get("TRIPSTACK_BASE_URL");
}