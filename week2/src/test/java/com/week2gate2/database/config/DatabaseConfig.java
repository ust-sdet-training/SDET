package com.week2gate2.database.config;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


public record DatabaseConfig(String jdbcUrl, String username, String password) {
    public static DatabaseConfig fromEnvironment() {
        return new DatabaseConfig(
                "jdbc:mysql://localhost:3306/order_db",
                decode("root"),
                decode("athul")
        );
    }

    static DatabaseConfig fromDatabaseUrl(String databaseUrl) {
        URI uri = URI.create(databaseUrl);
        String[] credentials = uri.getRawUserInfo().split(":", 2);
        String query = uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery();
        int port = uri.getPort() == -1 ? 5432 : uri.getPort();
        String convertedUrl = "jdbc:postgresql://" + uri.getHost() + ":" + port + uri.getPath() + query;

        return new DatabaseConfig(
                convertedUrl,
                decode(credentials[0]),
                decode(credentials[1])
        );
    }

    private static String required(String name) {
        String value = value(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable or system property: " + name);
        }
        return value;
    }

    private static String value(String name) {
        return config(name, null);
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    public static String config(String name, String fallback) {
        String system = System.getProperty(name);
        if (system != null && !system.isBlank()) {
            return system;
        }
        String envValue = System.getenv(name);
        return envValue == null || envValue.isBlank() ? fallback : envValue;
    }
}
