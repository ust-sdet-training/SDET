package com.ust.sdet.dbframework.config;

import com.ust.sdet.dbframework.support.TestEnvironment;

public record DatabaseConfig(String jdbcUrl, String username, String password) {

    public static DatabaseConfig fromEnvironment() {

        String jdbcUrl = TestEnvironment.required("DB_JDBC_URL");

            return new DatabaseConfig(
                    jdbcUrl,
                    required("DB_USER"),
                    required("DB_PASSWORD"));

    }


    private static String required(String name) {

        String value = value(name);

        if (value == null || value.isBlank()) {

            throw new IllegalStateException("Missing required environment variable or system property: " + name);

        }

        return value;

    }

    private static String value(String name) {

        return TestEnvironment.optional(name, null);

    }



}
