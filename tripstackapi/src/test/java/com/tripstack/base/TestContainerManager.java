package com.tripstack.base;

import com.tripstack.config.ConfigReader;
import org.testcontainers.containers.MySQLContainer;

public final class TestContainerManager {

    private static MySQLContainer<?> mysqlContainer;

    private TestContainerManager() {
    }

    public static synchronized void startContainer() {

        if (mysqlContainer == null) {

            mysqlContainer = new MySQLContainer<>(ConfigReader.getMysqlImage())
                    .withDatabaseName(ConfigReader.getDatabaseName())
                    .withUsername(ConfigReader.getDatabaseUsername())
                    .withPassword(ConfigReader.getDatabasePassword())
                    .withInitScript("sql/schema.sql");

            mysqlContainer.start();
        }
    }

    public static synchronized void stopContainer() {

        if (mysqlContainer != null) {
            mysqlContainer.stop();
            mysqlContainer = null;
        }
    }

    public static String getJdbcUrl() {
        return mysqlContainer.getJdbcUrl();
    }

    public static String getUsername() {
        return mysqlContainer.getUsername();
    }

    public static String getPassword() {
        return mysqlContainer.getPassword();
    }

    public static String getDatabaseName() {
        return mysqlContainer.getDatabaseName();
    }

    public static MySQLContainer<?> getContainer() {
        return mysqlContainer;
    }
}