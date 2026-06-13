package com.ust.sdet.api.DbFramework.Config;

import com.ust.sdet.api.DbFramework.Support.TestEnvironment;

public record DbConfig(
        String jdbcUrl,
        String username,
        String password
) {

    public static DbConfig fromEnvironment() {

        return new DbConfig(
                TestEnvironment.required("DB_JDBC_URL"),
                TestEnvironment.required("DB_USER"),
                TestEnvironment.required("DB_PASSWORD")
        );
    }
}