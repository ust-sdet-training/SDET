package week2.gate2.config;

import week2.gate2.support.TestEnvironment;

public record DatabaseConfig(
        String jdbcUrl,
        String username,
        String password
) {

    public static DatabaseConfig fromEnvironment() {

        return new DatabaseConfig(
                TestEnvironment.required("DB_JDBC_URL"),
                TestEnvironment.required("DB_USER"),
                TestEnvironment.required("DB_PASSWORD")
        );
    }
}