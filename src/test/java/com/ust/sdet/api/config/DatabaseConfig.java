package com.ust.sdet.api.config;

import com.ust.sdet.api.support.EnvCheck;

public record DatabaseConfig(String jdbcUrl, String username, String password) {

    public static DatabaseConfig fromEnvironment() {


        String jdbcUrl = String.format(
                "jdbc:mysql://%s:%s/%s",
                EnvCheck.required("DB_HOST"),
                EnvCheck.required("DB_PORT"),
                EnvCheck.required("DB_NAME")
        );


        return new DatabaseConfig(
                jdbcUrl,
                EnvCheck.required("DB_USER"),
                EnvCheck.required("DB_PASSWORD")
        );
    }

}