package api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.testcontainers.containers.MySQLContainer;

public final class DbSupport {

    private static volatile MySQLContainer<?> TEST_MYSQL;

    private DbSupport() {
    }

    public static boolean isConfigured() {
        return !DbConfig.getDbUrl().isBlank() && !DbConfig.getDbUser().isBlank() && !DbConfig.getDbPassword().isBlank();
    }

    public static Connection getConnection() throws SQLException {
        if (!isConfigured()) {
            throw new IllegalStateException("DB credentials are not configured in application.properties");
        }

        try {
            return DriverManager.getConnection(DbConfig.getDbUrl(), DbConfig.getDbUser(), DbConfig.getDbPassword());
        } catch (SQLException exception) {
            // If DB doesn't exist or is unreachable, try to create it on server first
            if (isUnknownDatabaseError(exception)) {
                try {
                    createDatabaseIfMissing();
                    return DriverManager.getConnection(DbConfig.getDbUrl(), DbConfig.getDbUser(), DbConfig.getDbPassword());
                } catch (SQLException e) {
                    // fall through to Testcontainers fallback
                }
            }

            // Start a Testcontainers MySQL instance as a fallback for CI/local runs
            synchronized (DbSupport.class) {
                if (TEST_MYSQL == null) {
                    TEST_MYSQL = new MySQLContainer<>("mysql:8.0").withDatabaseName("tripstack").withUsername("test").withPassword("test");
                    TEST_MYSQL.start();
                    System.setProperty("DB_URL", TEST_MYSQL.getJdbcUrl());
                    System.setProperty("DB_USER", TEST_MYSQL.getUsername());
                    System.setProperty("DB_PASSWORD", TEST_MYSQL.getPassword());
                }
            }

            return DriverManager.getConnection(DbConfig.getDbUrl(), DbConfig.getDbUser(), DbConfig.getDbPassword());
        }
    }

    private static boolean isUnknownDatabaseError(SQLException exception) {
        return exception.getErrorCode() == 1049
                || "42000" .equals(exception.getSQLState())
                || exception.getMessage().toLowerCase().contains("unknown database");
    }

    private static void createDatabaseIfMissing() throws SQLException {
        String databaseName = extractDatabaseName(DbConfig.getDbUrl());
        String serverUrl = stripDatabaseFromUrl(DbConfig.getDbUrl());

        try (Connection connection = DriverManager.getConnection(serverUrl, DbConfig.getDbUser(), DbConfig.getDbPassword());
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + databaseName + "`");
        }
    }

    private static String stripDatabaseFromUrl(String url) {
        int questionMarkIndex = url.indexOf('?');
        String baseUrl = questionMarkIndex > 0 ? url.substring(0, questionMarkIndex) : url;
        int lastSlash = baseUrl.lastIndexOf('/');
        if (lastSlash < 0) {
            throw new IllegalArgumentException("Invalid DB_URL format: " + url);
        }
        return (questionMarkIndex > 0 ? url.substring(0, lastSlash) : baseUrl.substring(0, lastSlash))
                + (questionMarkIndex > 0 ? url.substring(questionMarkIndex) : "");
    }

    private static String extractDatabaseName(String url) {
        int questionMarkIndex = url.indexOf('?');
        String baseUrl = questionMarkIndex > 0 ? url.substring(0, questionMarkIndex) : url;
        int lastSlash = baseUrl.lastIndexOf('/');
        if (lastSlash < 0 || lastSlash == baseUrl.length() - 1) {
            throw new IllegalArgumentException("Invalid DB_URL format: " + url);
        }
        return baseUrl.substring(lastSlash + 1);
    }

    public static Map<String, Object> findBookingByPnr(String pnr) {
        try (Connection connection = getConnection()) {
            String tableName = findBookingTable(connection);
            String pnrColumn = findColumn(connection, tableName, List.of("pnr"));

            String sql = SqlQueries.findBookingByPnr(tableName, pnrColumn);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, pnr);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new IllegalStateException("No booking row found for PNR: " + pnr);
                    }
                    return toMap(resultSet);
                }
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to read booking row from database", exception);
        }
    }

    public String readString(Map<String, Object> row, String... keys) {
        for (String key : keys) {
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                if (normalize(entry.getKey()).equals(normalize(key)) && entry.getValue() != null) {
                    return String.valueOf(entry.getValue());
                }
            }
        }
        return null;
    }

    private static String findBookingTable(Connection connection) throws SQLException {
        String tableName = querySingleValue(connection, SqlQueries.FIND_BOOKING_TABLE_BY_NAME);
        if (tableName == null) {
            tableName = querySingleValue(connection, SqlQueries.FIND_BOOKING_TABLE_BY_COLUMNS);
        }
        if (tableName != null) {
            return tableName;
        }
        throw new IllegalStateException("Unable to locate a booking table in the database");
    }

    private static String querySingleValue(Connection connection, String sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        }
        return null;
    }

    private static String findColumn(Connection connection, String tableName, List<String> keywords) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SqlQueries.findColumnByKeywords(keywords.size()))) {
            statement.setString(1, tableName);
            for (int index = 0; index < keywords.size(); index++) {
                statement.setString(index + 2, "%" + keywords.get(index).toLowerCase(Locale.ROOT) + "%");
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(1);
                }
            }
        }
        throw new IllegalStateException("Unable to resolve a column in table: " + tableName);
    }

    private static Map<String, Object> toMap(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();
        Map<String, Object> row = new LinkedHashMap<>();
        for (int column = 1; column <= columnCount; column++) {
            row.put(metadata.getColumnLabel(column), resultSet.getObject(column));
        }
        return row;
    }

    private String normalize(String value) {
        return value == null ? "" : value.replace("_", "").toLowerCase(Locale.ROOT);
    }
}
