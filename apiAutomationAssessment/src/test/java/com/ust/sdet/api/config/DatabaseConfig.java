//package com.ust.sdet.api.config;
//
//import com.ust.sdet.api.config.TestEnvironment;
//
//import java.net.URI;
//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//
//public record DatabaseConfig(String jdbcUrl, String username, String password) {
//    public static DatabaseConfig fromEnvironmentCredential() {
//        String jdbcUrl = value("DB_JDBC_URL");
//        if (jdbcUrl != null) {
//            return new DatabaseConfig(jdbcUrl, required("DB_USER"), required("DB_PASSWORD"));
////            return new DatabaseConfig(System.getenv("DB_JDBC_URL"),System.getenv("DB_USER"),System.getenv("DB_PASSWORD"));
//        }
//
//        String databaseUrl = required("DATABASE_URL");
//        return fromDatabaseUrl(databaseUrl);
//    }
//
//    static DatabaseConfig fromDatabaseUrl(String databaseUrl) {
//        String prefix = "mysql://";
//        if (!databaseUrl.startsWith(prefix)) {
//            throw new IllegalArgumentException("DATABASE_URL must start with mysql://");
//        }
//
//        int credentialsEnd = databaseUrl.lastIndexOf('@');
//        if (credentialsEnd < prefix.length()) {
//            throw new IllegalArgumentException("DATABASE_URL must include username and password");
//        }
//
//        String[] credentials = databaseUrl.substring(prefix.length(), credentialsEnd).split(":", 2);
//        if (credentials.length != 2) {
//            throw new IllegalArgumentException("DATABASE_URL must include username and password");
//        }
//
//        URI uri = URI.create(prefix + databaseUrl.substring(credentialsEnd + 1));
//        String query = uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery();
//        int port = uri.getPort() == -1 ? 3306 : uri.getPort();
//        String separator = query.isEmpty() ? "?" : query + "&";
//        String convertedUrl = "jdbc:mysql://" + uri.getHost() + ":" + port + uri.getPath()
//                + separator + "connectionTimeZone=UTC&forceConnectionTimeZoneToSession=true&allowPublicKeyRetrieval=true";
//
//        return new DatabaseConfig(
//                convertedUrl,
//                decode(credentials[0]),
//                decode(credentials[1])
//        );
//    }
//
//    private static String required(String name) {
//        String value = value(name);
//        if (value == null || value.isBlank()) {
//            throw new IllegalStateException("Missing required environment variable or system property: " + name);
//        }
//        return value;
//    }
//
//    private static String value(String name) {
//        return TestEnvironment.optional(name, null);
//    }
//
//    private static String decode(String value) {
//        return URLDecoder.decode(value, StandardCharsets.UTF_8);
//    }
//}
