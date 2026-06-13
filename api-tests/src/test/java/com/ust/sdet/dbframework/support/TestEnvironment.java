package com.ust.sdet.dbframework.support;

import io.github.cdimascio.dotenv.Dotenv;

public final class TestEnvironment {

    private static final Dotenv ENV = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();

    private TestEnvironment() {}

    public static String optional(String name, String fallback) {
        String system = System.getProperty(name);
        if (system != null && !system.isBlank()) {
            return system;
        }
        String env = System.getenv(name);
        if (env != null && !env.isBlank()) {
            return env;
        }
        String dotenv = ENV.get(name);
        if (dotenv != null && !dotenv.isBlank()) {
            return dotenv;
        }
        return fallback;
    }


    public static String required(String name) {
        String value = optional(name, null);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing configuration: " + name);
        }
        return value;
    }

}