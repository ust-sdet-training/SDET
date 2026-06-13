package com.ust.sdet.config;

import com.ust.sdet.dbframework.support.TestEnvironment;
import java.net.URI;

public record DatabaseConfig(
        String jdbcUrl,
        String username,
        String password
) {

    public static DatabaseConfig fromEnvironment() {

        String jdbc = TestEnvironment.optional(
                        "DB_JDBC_URL",
                        "");

        if (
                jdbc != null
        ) {

            return new DatabaseConfig(
                    jdbc,
                    TestEnvironment.optional(
                            "DB_USER",
                            ""
                    ),
                    TestEnvironment.optional(
                            "DB_PASSWORD",
                            ""
                    )
            );
        }

        String databaseUrl = TestEnvironment.optional(
                        "DATABASE_URL",
                        null
                );

        if (
                databaseUrl == null
        ) {

            throw new IllegalStateException(
                    "Either DB_JDBC_URL or DATABASE_URL must exist"
            );
        }

        return parseDatabaseUrl(
                databaseUrl
        );

    }

    private static DatabaseConfig parseDatabaseUrl(
            String url
    ) {

        URI uri = URI.create(url);
        String userInfo = uri.getUserInfo();
        String[] credentials = userInfo.split(":");

        return new DatabaseConfig(
                "jdbc:mysql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath(),
                credentials[0],
                credentials[1]
        );
    }
}