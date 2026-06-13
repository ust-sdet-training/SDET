package com.ust.sdet.api.dbframework.config;

import com.ust.sdet.api.dbframework.support.TestEnvironment;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public record DatabaseConfig(String jdbcUrl, String username, String password) {
    public static DatabaseConfig fromEnvironment() {
//        String jdbcUrl = value("DB_JDBC_URL");
//        if (jdbcUrl != null) {
//            return new DatabaseConfig(jdbcUrl, required("DB_USER"), required("DB_PASSWORD"));
//        }
//        String databaseUrl = required("DATABASE_URL");
//        return fromDatabaseUrl(databaseUrl);
        return new DatabaseConfig("jdbc:mysql://localhost:3306/orders_db",
                decode("root"),
                decode("root"));
    }
    static DatabaseConfig fromDatabaseUrl(String databaseUrl) {
        URI uri = URI.create(databaseUrl);
        String[] credentials = uri.getRawUserInfo().split(":", 2);
        String query = uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery();
        String scheme = uri.getScheme();
        int port;
        String jdbcPrefix;
        if ("postgresql".equalsIgnoreCase(scheme) || "postgres".equalsIgnoreCase(scheme)) {
            jdbcPrefix = "jdbc:postgresql://";
            port = uri.getPort() == -1 ? 5432 : uri.getPort();
        } else if ("mysql".equalsIgnoreCase(scheme)) {
            jdbcPrefix = "jdbc:mysql://";
            port = uri.getPort() == -1 ? 3306 : uri.getPort();
        } else {
            jdbcPrefix = "jdbc:" + scheme + "://";
            port = uri.getPort() == -1 ? -1 : uri.getPort();
        }
        String hostPort = port == -1 ? uri.getHost() : uri.getHost() + ":" + port;
        String convertedUrl = jdbcPrefix + hostPort + uri.getPath() + query;
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