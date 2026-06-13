package com.ust.sdet.api.dbframework.config;

import com.ust.sdet.api.dbframework.support.TestEnvironment;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public record DatabaseConfig(String jdbcUrl, String username, String password) {
    public static DatabaseConfig fromEnvironment() {
   return new
           DatabaseConfig(
                "jdbc:mysql://localhost:3306/orders_db",
                "root",
                "Dassbhai@2003"
        );
    }

    static DatabaseConfig fromDatabaseUrl(String databaseUrl) {
        URI uri = URI.create(databaseUrl);
        String[] credentials = uri.getRawUserInfo().split(":", 2);
        String query = uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery();
        int port = uri.getPort() == -1 ? 5432 : uri.getPort();
        String convertedUrl = "jdbc:mysql://" + uri.getHost() + ":" + port + uri.getPath() + query;

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
        return TestEnvironment.optional(name, null);
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}