package com.ust.capstone.data.secret;

import com.ust.capstone.config.Env;

public final class Secrets {

    private Secrets() {
    }

    public static String get(String key) {
        return Env.get(key);
    }
}