package config;

import support.TestEnvironment;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public record DatabaseConfig(
        String jdbcUrl,
        String username,
        String password) {

    public static DatabaseConfig fromEnvironment() {

        String jdbcUrl = value("DB_JDBC_URL");

        if (jdbcUrl != null && !jdbcUrl.isBlank()) {
            return new DatabaseConfig(
                    jdbcUrl,
                    required("DB_USER"),
                    required("DB_PASSWORD")
            );
        }

        String databaseUrl = required("DATABASE_URL");

        return fromDatabaseUrl(databaseUrl);
    }

    public static DatabaseConfig fromDatabaseUrl(String databaseUrl) {

        URI uri = URI.create(databaseUrl);

        String userInfo = uri.getRawUserInfo();

        if (userInfo == null || !userInfo.contains(":")) {
            throw new IllegalArgumentException(
                    "DATABASE_URL must contain username and password"
            );
        }

        String[] credentials = userInfo.split(":", 2);

        int port =
                uri.getPort() == -1
                        ? 3306
                        : uri.getPort();

        String query =
                uri.getRawQuery() == null
                        ? ""
                        : "?" + uri.getRawQuery();

        String jdbcUrl =
                "jdbc:mysql://"
                        + uri.getHost()
                        + ":"
                        + port
                        + uri.getPath()
                        + query;

        return new DatabaseConfig(
                jdbcUrl,
                decode(credentials[0]),
                decode(credentials[1])
        );
    }

    private static String required(String name) {

        String value = value(name);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required environment variable: " + name
            );
        }

        return value;
    }

    private static String value(String name) {
        return TestEnvironment.optional(name, null);
    }

    private static String decode(String value) {
        return URLDecoder.decode(
                value,
                StandardCharsets.UTF_8
        );
    }
}