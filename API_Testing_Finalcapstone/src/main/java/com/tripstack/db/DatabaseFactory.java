package com.tripstack.db;

import com.tripstack.config.TestConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseFactory {
    private static final boolean RUN_CONTAINER = Boolean.parseBoolean(System.getProperty("run.db.container", "false"));

    public static Connection createConnection() throws SQLException {
        if (RUN_CONTAINER) {
            return createContainerConnection();
        }

        try {
            return new DatabaseConnectionManager().getConnection();
        } catch (SQLException ex) {
            return createLocalFallbackConnection();
        }
    }

    public static Connection createContainerConnection() throws SQLException {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));
        container.start();
        return DriverManager.getConnection(container.getJdbcUrl(), container.getUsername(), container.getPassword());
    }

    private static Connection createLocalFallbackConnection() throws SQLException {
        String h2Url = "jdbc:h2:mem:tripstack-local;DB_CLOSE_DELAY=-1";
        return DriverManager.getConnection(h2Url, "sa", "");
    }
}
